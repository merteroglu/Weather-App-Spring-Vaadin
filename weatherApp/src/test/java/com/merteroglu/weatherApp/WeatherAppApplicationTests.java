package com.merteroglu.weatherApp;

import org.assertj.core.data.Offset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@RestClientTest(WeatherService.class)
public class WeatherAppApplicationTests {

	private static final String URL = "http://api.openweathermap.org/data/2.5/";

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private MockRestServiceServer server;

	@Test
	public void getWeather() {
		WeatherE forecast = this.weatherService.getWeather("es", "barcelona");
		assertThat(forecast.getName()).isEqualTo("Barcelona");
		assertThat(forecast.getTemperature()).isEqualTo(286.72, Offset.offset(0.1));
		assertThat(forecast.getWeatherId()).isEqualTo(800);
		assertThat(forecast.getWeatherIcon()).isEqualTo("01d");
	}

	@Test
	public void getWeatherForecast() {
		this.weatherService.getWeatherForecast("es", "barcelona");

	}

	@Test
	public void contextLoads() {
	}


}
