package es.esy.varto_novomyrgorod.varto.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.activity.ContainerActivity;
import es.esy.varto_novomyrgorod.varto.adapters.CatalogsAdapter;
import es.esy.varto_novomyrgorod.varto.database.dao.CatalogsDAO;
import es.esy.varto_novomyrgorod.varto.pojo.Catalog;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;
import es.esy.varto_novomyrgorod.varto.presenters.CatalogsPresenter;
import es.esy.varto_novomyrgorod.varto.view.CatalogView;
import es.esy.varto_novomyrgorod.varto.view.Toolbar;

public class CatalogsFragment extends MvpFragment<CatalogView, CatalogsPresenter>
        implements CatalogView, LoaderManager.LoaderCallbacks<List<Catalog>> {
    private static final String EXTRA_SHOP = "EXTRA_SHOP";
    private static final int LOADER_ID = 2;

    private Toolbar toolbar;
    private ListView list;
    private String shop;

    public static CatalogsFragment newInstance(Shop shop) {
        CatalogsFragment fragment = new CatalogsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SHOP, shop.toString());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalog, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        shop = getArguments().getString(EXTRA_SHOP);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        list = (ListView) getActivity().findViewById(R.id.list_catalog);
        list.setOnItemClickListener((parent, view, position, id) -> {
            String catalog = parent.getAdapter().getItem(position).toString();
            if (catalog != null) {
                ((ContainerActivity) getActivity())
                        .getNavigationManager()
                        .startGoods(Shop.valueOf(shop), catalog);
            }
        });
        setupShopLogo();
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
    }

    public void setupShopLogo() {
        ImageView logo = (ImageView) getActivity().findViewById(R.id.image_shop_logo);
        logo.setImageResource(Shop.valueOf(shop) == Shop.PLUS ?
                R.mipmap.logo_vartoplus : R.mipmap.logo_vartodishes);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setBackTitle(getString(R.string.toolbar_title_catalogs));
    }

    @NonNull
    @Override
    public CatalogsPresenter createPresenter() {
        return new CatalogsPresenter();
    }

    @Override
    public void display(List<Catalog> catalogs) {
        CatalogsAdapter adapter = new CatalogsAdapter(getActivity(), catalogs);
        list.setAdapter(adapter);
    }

    @Override
    public Loader<List<Catalog>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Catalog>>(getActivity().getApplicationContext()) {

            @Override
            public List<Catalog> loadInBackground() {
                return new CatalogsDAO(getActivity().getApplicationContext())
                        .getAll(Shop.valueOf(shop));
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Catalog>> loader, List<Catalog> data) {
        presenter.onLoadFinished(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Catalog>> loader) {
        presenter.onLoadFinished(new ArrayList<>());
    }
}
