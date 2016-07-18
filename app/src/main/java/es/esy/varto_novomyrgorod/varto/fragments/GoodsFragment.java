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
import es.esy.varto_novomyrgorod.varto.adapters.GoodsAdapter;
import es.esy.varto_novomyrgorod.varto.database.dao.GoodsDAO;
import es.esy.varto_novomyrgorod.varto.pojo.Good;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;
import es.esy.varto_novomyrgorod.varto.presenters.GoodsPresenter;
import es.esy.varto_novomyrgorod.varto.view.GoodView;
import es.esy.varto_novomyrgorod.varto.view.Toolbar;

public class GoodsFragment extends MvpFragment<GoodView, GoodsPresenter>
        implements GoodView, LoaderManager.LoaderCallbacks<List<Good>> {
    private static final String TAG = "GoodsFragment";
    private static final String EXTRA_SHOP = "EXTRA_SHOP";
    private static final String EXTRA_CATALOG = "EXTRA_CATALOG";
    private static final int LOADER_ID = 2;

    private Toolbar toolbar;
    private ListView goodsList;

    private String catalog;
    private String shop;

    public static GoodsFragment newInstance(Shop shop, String catalog) {
        GoodsFragment fragment = new GoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SHOP, shop.toString());
        bundle.putString(EXTRA_CATALOG, catalog);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        goodsList = (ListView) getActivity().findViewById(R.id.list_goods);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        shop = getArguments().getString(EXTRA_SHOP);
        catalog = getArguments().getString(EXTRA_CATALOG);
        Log.d(TAG, "onStart: " + catalog);
        setupShopLogo();
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
    }

    public void setupShopLogo() {
        ImageView logo = (ImageView) getActivity().findViewById(R.id.image_shop_logo);
        logo.setImageResource(Shop.valueOf(shop) == Shop.PLUS ?
                R.drawable.logo_vartoplus : R.drawable.logo_vartodishes);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setBackTitle(getString(R.string.toolbar_title_goods));
    }

    @NonNull
    @Override
    public GoodsPresenter createPresenter() {
        return new GoodsPresenter();
    }

    @Override
    public void display(List<Good> goods) {
        GoodsAdapter adapter = new GoodsAdapter(getActivity(), goods);
        goodsList.setAdapter(adapter);
    }

    @Override
    public Loader<List<Good>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Good>>(getActivity().getApplicationContext()) {

            @Override
            public List<Good> loadInBackground() {
                return new GoodsDAO(getActivity()
                        .getApplicationContext())
                        .getAll(Shop.valueOf(shop), catalog);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Good>> loader, List<Good> data) {
        presenter.onLoadFinished(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Good>> loader) {
        presenter.onLoadFinished(new ArrayList<>());
    }
}
