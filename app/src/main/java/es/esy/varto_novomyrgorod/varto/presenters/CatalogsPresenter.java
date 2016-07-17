package es.esy.varto_novomyrgorod.varto.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.Catalog;
import es.esy.varto_novomyrgorod.varto.view.CatalogView;

public class CatalogsPresenter extends MvpBasePresenter<CatalogView> {

    public void onLoadFinished(List<Catalog> catalogs) {
        if (getView() != null) {
            getView().display(catalogs);
        }
    }
}
