package es.esy.varto_novomyrgorod.varto.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.adapters.NewsAdapter;
import es.esy.varto_novomyrgorod.varto.database.dao.NewsDAO;
import es.esy.varto_novomyrgorod.varto.pojo.News;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;
import es.esy.varto_novomyrgorod.varto.presenters.NewsPresenter;
import es.esy.varto_novomyrgorod.varto.view.NewsView;
import es.esy.varto_novomyrgorod.varto.view.Toolbar;

public class NewsFragment extends MvpFragment<NewsView, NewsPresenter>
        implements NewsView, LoaderManager.LoaderCallbacks<List<News>> {
    private static final String TAG = "NewsFragment";
    private static final String EXTRA_SHOP = "EXTRA_SHOP";
    private static final int LOADER_ID = 1;

    private ListView newsList;
    private Toolbar toolbar;
    private String shop;

    public static NewsFragment newInstance(Shop shop) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SHOP, shop.toString());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        newsList = (ListView) getActivity().findViewById(R.id.list_news);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        shop = getArguments().getString(EXTRA_SHOP);
        setupShopLogo();
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
    }

    private void setupShopLogo() {
        ImageView logo = (ImageView) getActivity().findViewById(R.id.image_shop_logo);
        logo.setImageResource(Shop.valueOf(shop) == Shop.PLUS ?
                R.mipmap.logo_vartoplus : R.mipmap.logo_vartodishes);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setBackTitle(getString(R.string.toolbar_title_news));
    }

    @NonNull
    @Override
    public NewsPresenter createPresenter() {
        return new NewsPresenter();
    }

    @Override
    public void display(List<News> news) {
        if (news != null) Log.d(TAG, "display: size - " + news.size());
        NewsAdapter adapter = new NewsAdapter(getActivity(), news);
        newsList.setAdapter(adapter);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: ");
        return new AsyncTaskLoader<List<News>>(getActivity().getApplicationContext()) {

            @Override
            public List<News> loadInBackground() {
                return new NewsDAO(getActivity()
                        .getApplicationContext())
                        .getAll(Shop.valueOf(shop));
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        Log.d(TAG, "onLoadFinished: ");
        presenter.onLoadFinished(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.d(TAG, "onLoaderReset: ");
        presenter.onLoadFinished(new ArrayList<>());
    }
}