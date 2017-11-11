package com.merteroglu.weatherApp;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
public class CitysLayout extends VerticalLayout{

    @Autowired
    CityRepository repo;

    @PostConstruct
    void init(){
        update();
    }

    void update(){
        setCitys(repo.findAll());
    }

    private void setCitys(List<City> citys) {
        removeAllComponents();
        citys.forEach(city -> addComponent(new CityItemLayout(city)));
    }


    public void add(City city) {
        repo.save(city);
        update();
    }
}
