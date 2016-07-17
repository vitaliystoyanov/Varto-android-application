package es.esy.varto_novomyrgorod.varto.view;

import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.MvpView;

import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public interface MainView extends MvpView {

    void displayMenu(Shop shop);

    void showQuantityContent(Bundle bundle);

    void hideQuantityContent();
}
