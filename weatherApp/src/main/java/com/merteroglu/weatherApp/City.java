package com.merteroglu.weatherApp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String text;

    public City(){

    }

    public City(String cityName){
        text = cityName;
    }

    public String getName() {
        return text;
    }

    public void setName(String name) {
        this.text = name;
    }
}
