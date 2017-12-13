package com.merteroglu.weatherApp;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@RestClientTest(WeatherService.class)
public class WeatherAppApplicationTests {

	private static final String URL = "http://api.openweathermap.org/data/2.5/";

	RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

	private WeatherService weatherService = new WeatherService(restTemplateBuilder);

	@Test
	public void getWeather() {
		WeatherE weather = this.weatherService.getWeather("es", "barcelona");
		Assert.assertEquals("Barcelona",weather.getName());
		Assert.assertEquals(800, weather.getWeatherId().intValue());
	}

	@Test
	public void getWeatherForecast() {
		WeatherForecast forecast = this.weatherService.getWeatherForecast("es", "barcelona");
		Assert.assertEquals("Barcelona",forecast.getName());
	}


}
