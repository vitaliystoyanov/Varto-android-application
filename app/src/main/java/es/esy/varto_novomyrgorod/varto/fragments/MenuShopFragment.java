package es.esy.varto_novomyrgorod.varto.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.activity.ContainerActivity;
import es.esy.varto_novomyrgorod.varto.database.dao.ScheduleDAO;
import es.esy.varto_novomyrgorod.varto.pojo.Schedule;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;
import es.esy.varto_novomyrgorod.varto.presenters.MenuShopPresenter;
import es.esy.varto_novomyrgorod.varto.view.MenuShopView;
import es.esy.varto_novomyrgorod.varto.view.Toolbar;

public class MenuShopFragment extends MvpFragment<MenuShopView, MenuShopPresenter>
        implements MenuShopView, View.OnClickListener, LoaderManager.LoaderCallbacks<Schedule> {
    public static final String TAG = "MenuShopFragment";

    private static final String EXTRA_SHOP = "EXTRA_SHOP";
    private static final int DURATION_MILLIS_ANIMATION = 400;
    private static final int LOADER_ID = 4;

    private ImageView goodsImage;
    private ImageView newsImage;
    private String shop;
    private Toolbar toolbar;

    public static MenuShopFragment newInstance(Shop shop) {
        MenuShopFragment fragment = new MenuShopFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SHOP, shop.toString());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        shop = getArguments().getString(EXTRA_SHOP);

        goodsImage = (ImageView) getActivity().findViewById(R.id.image_goods);
        newsImage = (ImageView) getActivity().findViewById(R.id.image_news);

        TextView news = (TextView) getActivity().findViewById(R.id.text_menu_news);
        news.setOnClickListener(this);
        TextView goods = (TextView) getActivity().findViewById(R.id.menu_goods);
        goods.setOnClickListener(this);
        setupBackgroundImages();

        TextView location = (TextView) getActivity().findViewById(R.id.title_location);
        TextView email = (TextView) getActivity().findViewById(R.id.title_email);
        loadDescription(location, email);

        setupShopLogo();
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
    }

    private void loadDescription(TextView textViewLocation, TextView textViewEmail) {
        if (shop.equals(Shop.PLUS.toString())) {
            textViewLocation.setText(R.string.location_varto_plus);
            textViewEmail.setText(R.string.email_varto_plus);
        } else if (shop.equals(Shop.DISHES.toString())) {
            textViewLocation.setText(R.string.location_varto_dishes);
            textViewEmail.setText(R.string.email_varto_dishes);
        }
    }

    private void setupBackgroundImages() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(DURATION_MILLIS_ANIMATION, true, true, true))
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("assets://images/wine.jpg", newsImage, options);
        imageLoader.displayImage("assets://images/goods.jpg", goodsImage, options);
    }

    private void setupShopLogo() {
        ImageView logo = (ImageView) getActivity().findViewById(R.id.image_shop_logo);
        logo.setImageResource(Shop.valueOf(shop) == Shop.PLUS ?
                R.drawable.logo_vartoplus : R.drawable.logo_vartodishes);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setVisibleBackButton(true);
        toolbar.setBackTitle(getString(R.string.toolbar_title_menu));
    }

    @NonNull
    @Override
    public MenuShopPresenter createPresenter() {
        return new MenuShopPresenter();
    }

    @Override
    public void displayNews() {
        ((ContainerActivity) getActivity()).getNavigationManager().startNews(Shop.valueOf(shop));
    }

    @Override
    public void displayCatalogs() {
        ((ContainerActivity) getActivity()).getNavigationManager().startCatalog(Shop.valueOf(shop));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (R.id.text_menu_news == i) {
            presenter.onClickNews();
        } else if (R.id.menu_goods == i) {
            presenter.onClickGoods();
        }
    }

    @Override
    public void displaySchedule(Schedule schedule) {
        TextView timetable = (TextView) getActivity().findViewById(R.id.title_schedule);
        String prefixTimetable = getResources().getString(R.string.title_schedule_today);
        String result = prefixTimetable + " " + schedule.getScheduleToday();
        timetable.setText(result);
    }

    @Override
    public Loader<Schedule> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Schedule>(getActivity().getApplicationContext()) {

            @Override
            public Schedule loadInBackground() {
                return new ScheduleDAO(getActivity()
                        .getApplicationContext())
                        .get(Shop.valueOf(shop));
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Schedule> loader, Schedule data) {
        presenter.onLoadFinished(data);
    }

    @Override
    public void onLoaderReset(Loader<Schedule> loader) {
        presenter.onLoadFinished(new Schedule());
    }
}