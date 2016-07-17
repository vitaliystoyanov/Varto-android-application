package es.esy.varto_novomyrgorod.varto.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.News;

public interface NewsView extends MvpView {

    void display(List<News> news);

}
