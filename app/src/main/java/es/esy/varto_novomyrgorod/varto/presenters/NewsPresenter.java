package es.esy.varto_novomyrgorod.varto.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.News;
import es.esy.varto_novomyrgorod.varto.view.NewsView;

public class NewsPresenter extends MvpBasePresenter<NewsView> {

    public void onLoadFinished(List<News> data) {
        if (getView() != null) {
            getView().display(data);
        }
    }
}
