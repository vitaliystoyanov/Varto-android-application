package es.esy.varto_novomyrgorod.varto.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.Catalog;

public interface CatalogView extends MvpView {

    void display(List<Catalog> catalogs);

}
