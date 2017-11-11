package com.merteroglu.weatherApp;

import com.vaadin.data.Binder;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


public class CityItemLayout extends VerticalLayout {


    private final Label cityName;
    private final Label weather;
    private final Image image;

    WeatherSummaryController service;

    public CityItemLayout(City city) {
        cityName = new Label();
        weather = new Label();

        cityName.addStyleName(ValoTheme.LABEL_H2);
        weather.addStyleName(ValoTheme.LABEL_H2);

        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        setWidth("80%");



        addComponents(cityName);

        service = new WeatherSummaryController();

       WeatherSummary summary = service.getSummary(city.getName());

        HorizontalLayout horizontalLayout = new HorizontalLayout();



        image = new Image(null,  new ExternalResource("http://openweathermap.org/img/w/" + summary.getIcon() + ".png") );

        horizontalLayout.addComponents(image,weather);

        addComponent(horizontalLayout);

       cityName.setCaption(summary.getCity() + "," + summary.getCountry());
       weather.setCaption(summary.getCelsiusTemperature() + "°C");




    }
}
