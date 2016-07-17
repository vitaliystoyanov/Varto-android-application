package es.esy.varto_novomyrgorod.varto.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import es.esy.varto_novomyrgorod.varto.pojo.Schedule;

public interface MenuShopView extends MvpView {

    void displayNews();

    void displayCatalogs();

    void displaySchedule(Schedule schedule);

}
