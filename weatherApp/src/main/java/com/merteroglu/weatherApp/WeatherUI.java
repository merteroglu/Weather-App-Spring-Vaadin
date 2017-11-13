package com.merteroglu.weatherApp;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

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
        addDeleteButton();
    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.TOP_LEFT);
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
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("80%");
        TextField cityName = new TextField();
        Button add = new Button("Add");
        add.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        add.setIcon(VaadinIcons.PLUS_CIRCLE);

        add.addClickListener(click -> {
            citysLayout.add(new City(cityName.getValue()));
            cityName.clear();
            cityName.focus();
        });

        add.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        formLayout.addComponentsAndExpand(cityName);
        formLayout.addComponent(add);
        root.addComponent(formLayout);
    }

    private void addWeatherList() {
        citysLayout.setWidth("80%");
        root.addComponent(citysLayout);
    }


    private void addDeleteButton() {
        root.addComponent(new Button("Delete All"));
    }

}
