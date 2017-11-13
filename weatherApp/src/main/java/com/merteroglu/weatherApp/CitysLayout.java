package com.merteroglu.weatherApp;

import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Logger;

@SpringComponent
public class CitysLayout extends VerticalLayout{

    Logger log = Logger.getLogger("My Logs");

    @Autowired
    CityRepository repo;

    final int page = 1;
    final int limit = 5;
    long total;


    @PostConstruct
    void init() {
        setDefaultComponentAlignment(Alignment.TOP_LEFT);
        update();
    }


    void update(){
        total = repo.count();
        log.info("total :" + total);
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
