package es.esy.varto_novomyrgorod.varto.presenters;

import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import es.esy.varto_novomyrgorod.varto.pojo.Shop;
import es.esy.varto_novomyrgorod.varto.view.MainView;

public class MainPresenter extends MvpBasePresenter<MainView> {

    public void goTo(Shop shop) {
        if (getView() != null) {
            getView().displayMenu(shop);
            getView().hideQuantityContent();
        }
    }

    public void onCreate(Bundle bundle) {
        if (getView() != null && bundle != null) {
            getView().showQuantityContent(bundle);
        }
    }
}
