package com.merteroglu.weatherApp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WeatherSummaryController {


    private final WeatherService weatherService;

    private final WeatherAppProperties properties;

    public WeatherSummaryController(WeatherService weatherService, WeatherAppProperties properties) {
        this.weatherService = weatherService;
        this.properties = properties;
    }

    public WeatherSummaryController(){
        WeatherAppProperties wap = new WeatherAppProperties();
        this.weatherService = new WeatherService(new RestTemplateBuilder(),wap);
        this.properties = wap;
    }

    private List<WeatherSummary> getSummary() {
        List<WeatherSummary> summary = new ArrayList<>();
        for (String location : this.properties.getLocations()) {
            String country = location.split("/")[0];
            String city = location.split("/")[1];
            WeatherE weather = this.weatherService.getWeather(country, city);
            summary.add(createWeatherSummary(country, city, weather));
        }
        return summary;
    }

    public WeatherSummary getSummary(String text){
        WeatherSummary summary;
        String country = text.split("/")[0];
        String city = text.split("/")[1];
        WeatherE weather = this.weatherService.getWeather(country,city);
        summary = createWeatherSummary(country,city,weather);
        return summary;
    }

    private WeatherSummary createWeatherSummary(String country, String city,
                                                WeatherE weather) {
        // cough cough
        if ("Las Vegas".equals(city)) {
            weather.setWeatherId(666);
        }
        return new WeatherSummary(country, city, weather);
    }

}
