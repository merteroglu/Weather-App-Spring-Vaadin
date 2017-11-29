package com.merteroglu.weatherApp;

import javax.persistence.*;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
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
