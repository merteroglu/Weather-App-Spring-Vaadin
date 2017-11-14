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

        setWidth("100%");

        cityName = new Label();
        weather = new Label();

        cityName.addStyleName(MaterialTheme.LABEL_H2);
        weather.addStyleName(MaterialTheme.LABEL_H3);

        service = new WeatherSummaryController();

        WeatherSummary summary = service.getSummary(city.getName());

        image = new Image(null,  new ExternalResource("http://openweathermap.org/img/w/" + summary.getIcon() + ".png") );

        VerticalLayout thisDayLayout = new VerticalLayout();
        cityName.setWidth("90%");
        thisDayLayout.addComponents(cityName,image,weather);
        thisDayLayout.setStyleName(MaterialTheme.LAYOUT_WELL);
        thisDayLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addComponent(thisDayLayout);

        cityName.setValue(summary.getCity() + "," + summary.getCountry());
        weather.setValue(summary.getCelsiusTemperature() + "°C" );

        WeatherForecast forecast = service.getForecast(city.getName());

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateFormat formatterDay = new SimpleDateFormat("EEEE");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout verticalLayoutDays = new VerticalLayout();

        horizontalLayout.setWidth("100%");
        horizontalLayout.setStyleName(MaterialTheme.LAYOUT_CARD);
        horizontalLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        horizontalLayout.setVisible(false);


        Button gizleGoster = new Button("Haftalık Hava durumunu göster");
        gizleGoster.setStyleName(MaterialTheme.BUTTON_PRIMARY);

        gizleGoster.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(visiblity == 0){
                    horizontalLayout.setVisible(true);
                    visiblity = 1;
                    gizleGoster.setCaption("Haftalık Hava durumunu gizle");
                }else{
                    horizontalLayout.setVisible(false);
                    visiblity = 0;
                    gizleGoster.setCaption("Haftalık Hava durumunu göster");
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
                    //logger.info("Gunler : " + date.getDay() + " , " + formatterDay.format(date) + forecast.getEntries().get(i).getTimestamp() );
                    if(temp != date.getDay()){
                        temp = date.getDay();
                        verticalLayoutDays = new VerticalLayout();
                        verticalLayoutDays.setStyleName(MaterialTheme.LAYOUT_WELL);
                        verticalLayoutDays.setDefaultComponentAlignment(Alignment.TOP_CENTER);
                        horizontalLayout.addComponent(verticalLayoutDays);
                        Label day = new Label();
                        day.setStyleName(MaterialTheme.LABEL_BOLD);
                        Label hours = new Label();
                        hours.setStyleName(MaterialTheme.LABEL_COLORED);
                        Label weather = new Label();
                        weather.setStyleName(MaterialTheme.LABEL_H3);
                        Image weatherIcon = new Image(null,new ExternalResource("http://openweathermap.org/img/w/"+ forecast.getEntries().get(i).getWeatherIcon() + ".png"));
                        day.setValue(formatterDay.format(date));
                        hours.setValue(String.valueOf(date.getHours()) + ":" + String.valueOf( date.getMinutes()) + "0");
                        weather.setValue(forecast.getEntries().get(i).getCelsiusTemperature() + "°C");
                        verticalLayoutDays.addComponents(day,hours,weatherIcon,weather);
                    }else{
                        Label hours = new Label();
                        hours.setStyleName(MaterialTheme.LABEL_COLORED);
                        Label weather = new Label();
                        weather.setStyleName(MaterialTheme.LABEL_H3);
                        Image weatherIcon = new Image(null,new ExternalResource("http://openweathermap.org/img/w/"+ forecast.getEntries().get(i).getWeatherIcon()+".png"));
                        hours.setValue(String.valueOf(date.getHours()) + ":" + String.valueOf( date.getMinutes()) + "0");
                        weather.setValue(forecast.getEntries().get(i).getCelsiusTemperature() + "°C");
                        verticalLayoutDays.addComponents(hours,weatherIcon,weather);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

        addComponent(horizontalLayout);

    }
}
