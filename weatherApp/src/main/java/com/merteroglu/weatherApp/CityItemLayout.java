package com.merteroglu.weatherApp;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class CityItemLayout extends VerticalLayout {

    private static final Logger logger = LoggerFactory.getLogger(CityItemLayout.class);

    private final Label cityName;
    private final Label weather;
    private final Image image;

    WeatherSummaryController service;

    public CityItemLayout(City city) {
        setDefaultComponentAlignment(Alignment.TOP_LEFT);
        setStyleName(ValoTheme.LAYOUT_CARD);
        setResponsive(true);
        setWidth("80%");

        cityName = new Label();
        weather = new Label();

        cityName.addStyleName(MaterialTheme.LABEL_H3);
        weather.addStyleName(MaterialTheme.LABEL_H3);


        service = new WeatherSummaryController();

        WeatherSummary summary = service.getSummary(city.getName());

        image = new Image(null,  new ExternalResource("http://openweathermap.org/img/w/" + summary.getIcon() + ".png") );

        addComponents(cityName,image,weather);

        cityName.setValue(summary.getCity() + "," + summary.getCountry());
        weather.setValue(summary.getCelsiusTemperature() + "°C" );

        List<WeatherSummary> summaryList = service.getForecast(city.getName());

        for(int i = 0;i<summaryList.size();i++){
            //logger.info(i + "." + summaryList.get(i).getCity() + " " + summaryList.get(i).);
        }

    }
}
