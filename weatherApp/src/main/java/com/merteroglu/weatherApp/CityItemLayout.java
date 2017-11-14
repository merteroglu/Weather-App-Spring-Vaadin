package com.merteroglu.weatherApp;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CityItemLayout extends VerticalLayout {

    private static final Logger logger = LoggerFactory.getLogger(CityItemLayout.class);

    private final Label cityName;
    private final Label weather;
    private final Image image;
    int visiblity = 0;
    WeatherSummaryController service;

    public CityItemLayout(City city) {
        setDefaultComponentAlignment(Alignment.TOP_LEFT);
        setStyleName(ValoTheme.LAYOUT_CARD);
        setResponsive(true);


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

        WeatherForecast forecast = service.getForecast(city.getName());

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'");
        DateFormat formatterDay = new SimpleDateFormat("EEEE");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout verticalLayoutDays = new VerticalLayout();

        horizontalLayout.setVisible(false);


        Button gizleGoster = new Button("Haftalık Hava durumu göster");

        gizleGoster.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(visiblity == 0){
                    horizontalLayout.setVisible(true);
                    visiblity = 1;
                }else{
                    horizontalLayout.setVisible(false);
                    visiblity = 0;
                }
            }
        });

        addComponent(gizleGoster);

        int temp = -1;
        for(int i = 0;i<forecast.getEntries().size();i++){
                //logger.info("i : " + i  + " " + forecast.getEntries().get(i).getTimestamp());
                try {
                    Date date = dateFormatter.parse(forecast.getEntries().get(i).getTimestamp().toString());
                    //logger.info("i :" + i + " " + formatterDay.format(date) );
                    if(temp != date.getDay()){
                        temp = date.getDay();
                        verticalLayoutDays = new VerticalLayout();
                        horizontalLayout.addComponent(verticalLayoutDays);
                        Label day = new Label();
                        Label hours = new Label();
                        Label weather = new Label();
                        Image weatherIcon = new Image(null,new ExternalResource("http://openweathermap.org/img/w/"+ forecast.getEntries().get(i).getWeatherIcon() + ".png"));
                        day.setValue(formatterDay.format(date));
                        hours.setValue(String.valueOf(date.getHours()) + ":" + String.valueOf( date.getMinutes()) + "0");
                        weather.setValue(forecast.getEntries().get(i).getCelsiusTemperature() + "°C");
                        verticalLayoutDays.addComponents(day,hours,weather,weatherIcon);
                    }else{
                        Label hours = new Label();
                        Label weather = new Label();
                        Image weatherIcon = new Image(null,new ExternalResource("http://openweathermap.org/img/w/"+ forecast.getEntries().get(i).getWeatherIcon()+".png"));
                        hours.setValue(String.valueOf(date.getHours()) + ":" + String.valueOf( date.getMinutes()) + "0");
                        weather.setValue(forecast.getEntries().get(i).getCelsiusTemperature() + "°C");
                        verticalLayoutDays.addComponents(hours,weather,weatherIcon);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

        addComponent(horizontalLayout);

    }
}
