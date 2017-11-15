package com.merteroglu.weatherApp;

import com.github.appreciated.material.MaterialTheme;
import com.vaadin.addon.pagination.Pagination;
import com.vaadin.addon.pagination.PaginationChangeListener;
import com.vaadin.addon.pagination.PaginationResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringComponent
public class CitysLayout extends VerticalLayout{

    Logger log = Logger.getLogger("My Logs");

    @Autowired
    CityRepository repo;

    final int limit = 5;
    long total;
    int page = 1;
    int k ;

    List<City> fiveCity = new ArrayList<>();

    HorizontalLayout pageLayout;

    @PostConstruct
    void init() {
        setDefaultComponentAlignment(Alignment.TOP_LEFT);
        setWidth("100%");
        update();
    }


    void update(){
        removeAllComponents();
        pageLayout = new HorizontalLayout();
        pageLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        total = repo.count();
        log.info("total :" + total);
        List<City> fiveCity = new ArrayList<>();
        List<City> allCity = repo.findAll();

        if(total > 5){

            for(int i = (page*5)-5;i<((page-1)*5)+5;i++){
                if(i < allCity.size()){
                    fiveCity.add(allCity.get(i));
                }
            }

            for(k = 0; k < (total/5)+1;k++){
                Button btn = new Button((k+1)+"");
                btn.setStyleName(MaterialTheme.BUTTON_FLAT);

                Button.ClickListener clickListener = new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        page = Integer.parseInt( btn.getCaption());
                        update();
                    }
                };
                btn.addClickListener(clickListener);
                pageLayout.addComponent(btn);
            }
            setCitys(fiveCity);
            addComponent(pageLayout);
        }else{
            setCitys(repo.findAll());
        }

    }

    private void setCitys(List<City> citys) {
       // removeAllComponents();
        citys.forEach(city -> addComponent(new CityItemLayout(city)));
    }


    public void add(City city) {
        try{
            String country = city.getName().split("/")[0];
            String cityy = city.getName().split("/")[1];

        }catch (Exception e){
            log.info(e.getMessage().toString());
            Notification.show("Hata",
                    "Sehir bilgisi eklenemedi. Girdi Country/City seklinde olmalidir ",
                    Notification.Type.HUMANIZED_MESSAGE);
        }

        try{
            repo.save(city);
            update();
        }catch (Exception e){
            Notification.show("Hata",
                    "Sehir bilgisi daha önce kayıt edilmis veya hatalı ",
                    Notification.Type.HUMANIZED_MESSAGE);
        }

    }
}
