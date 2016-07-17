package es.esy.varto_novomyrgorod.varto.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.Good;
import es.esy.varto_novomyrgorod.varto.view.GoodView;

public class GoodsPresenter extends MvpBasePresenter<GoodView> {

    public void onLoadFinished(List<Good> data) {
        if (getView() != null) {
            getView().display(data);
        }
    }
}
