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

    public WeatherSummaryController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public WeatherSummaryController(){
        this.weatherService = new WeatherService(new RestTemplateBuilder());
    }

    public WeatherSummary getSummary(String text){
        WeatherSummary summary;
            String country = text.split("/")[0];
            String city = text.split("/")[1];
            WeatherE weather = this.weatherService.getWeather(country, city);
            summary = createWeatherSummary(country, city, weather);
        return summary;
    }

    public List<WeatherSummary> getForecast(String text){
        WeatherForecast forecast;
        List<WeatherSummary> summary = new ArrayList<>();
            String country = text.split("/")[0];
            String city = text.split("/")[1];
            forecast = this.weatherService.getWeatherForecast(country,city);
            for(int i = 0;i<forecast.getEntries().size();i++){
                summary.add(createWeatherSummary(country,city,forecast.getEntries().get(i))) ;
            }
        return summary;
    }

    private WeatherSummary createWeatherSummary(String country, String city, WeatherE weather) {
        return new WeatherSummary(country, city, weather);
    }

    private WeatherSummary createWeatherSummary(String country, String city, WeatherEntry weather) {
        return new WeatherSummary(country, city, weather);
    }

}
