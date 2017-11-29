package com.merteroglu.weatherApp;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@Title("Weather App")
@SpringUI
@Theme("valo")
public class WeatherUI extends UI {

    private VerticalLayout root;

    @Autowired
    CitysLayout citysLayout;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addHeader();
        addForm();
        addWeatherList();
    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        root.setResponsive(true);
        setContent(root);
    }

    private void addHeader() {
       Label header = new Label("Weather App - Vaadin");
       header.setWidth("80%");
       header.addStyleName(ValoTheme.LABEL_H1);
       root.addComponent(header);
    }

    private void addForm() {
        VerticalLayout vformLayout = new VerticalLayout();
        vformLayout.setStyleName(MaterialTheme.LAYOUT_WELL);
        vformLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        vformLayout.setWidth("80%");
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("70%");
        TextField cityName = new TextField();
        cityName.setStyleName(MaterialTheme.TEXTFIELD_CUSTOM);
        cityName.setDescription("Country/City");
        Button add = new Button("Add");
        add.addStyleName(MaterialTheme.BUTTON_FRIENDLY);
        add.setIcon(VaadinIcons.PLUS_CIRCLE);

        add.addClickListener(click -> {
            citysLayout.add(new City(cityName.getValue()));
            cityName.clear();
            cityName.focus();
        });

        add.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        formLayout.addComponentsAndExpand(cityName);
        formLayout.addComponent(add);
        Label sehirEkle = new Label("Sehir Ekle");
        sehirEkle.setWidth("15%");
        sehirEkle.setStyleName(MaterialTheme.LABEL_H3);
        vformLayout.addComponent(sehirEkle);
        vformLayout.addComponent(formLayout);
        root.addComponent(vformLayout);
    }

    private void addWeatherList() {
        citysLayout.setWidth("80%");
        root.addComponent(citysLayout);
    }


}
