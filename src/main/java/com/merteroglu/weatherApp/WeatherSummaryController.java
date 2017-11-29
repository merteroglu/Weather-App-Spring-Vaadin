package com.merteroglu.weatherApp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WeatherSummaryController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherSummaryController.class);

    private final WeatherService weatherService;

    public WeatherSummaryController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public WeatherSummaryController(){
        this.weatherService = new WeatherService(new RestTemplateBuilder());
    }

    public WeatherSummary getSummary(String text){
        WeatherSummary summary = null;
        try {
            String country = text.split("/")[0];
            String city = text.split("/")[1];
            WeatherE weather = this.weatherService.getWeather(country, city);
            summary = createWeatherSummary(country, city, weather);
        }catch (Exception e){
            logger.info("Exception : " + e.toString());
            return null;
        }
            return summary;
    }

    public WeatherForecast getForecast(String text){
            WeatherForecast forecast = null;
            try {
                String country = text.split("/")[0];
                String city = text.split("/")[1];
                forecast = this.weatherService.getWeatherForecast(country, city);
            }catch (Exception e){
                logger.info("Exception : " + e.toString());
                return null;
            }
            return forecast;
    }

    private WeatherSummary createWeatherSummary(String country, String city, WeatherE weather) {
        return new WeatherSummary(country, city, weather);
    }

    private WeatherSummary createWeatherSummary(String country, String city, WeatherEntry weather) {
        return new WeatherSummary(country, city, weather);
    }

}
