package es.esy.varto_novomyrgorod.varto.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import es.esy.varto_novomyrgorod.varto.pojo.Schedule;
import es.esy.varto_novomyrgorod.varto.view.MenuShopView;

public class MenuShopPresenter extends MvpBasePresenter<MenuShopView> {

    public void onClickNews() {
        if (getView() != null) {
            getView().displayNews();
        }
    }

    public void onClickGoods() {
        if (getView() != null) {
            getView().displayCatalogs();
        }
    }

    public void onLoadFinished(Schedule data) {
        if (getView() != null) {
            getView().displaySchedule(data);
        }
    }
}
