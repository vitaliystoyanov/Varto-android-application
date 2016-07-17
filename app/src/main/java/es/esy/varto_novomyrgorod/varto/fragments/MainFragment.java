package es.esy.varto_novomyrgorod.varto.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.activity.ContainerActivity;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;
import es.esy.varto_novomyrgorod.varto.presenters.MainPresenter;
import es.esy.varto_novomyrgorod.varto.service.ContentIntentService;
import es.esy.varto_novomyrgorod.varto.view.MainView;
import es.esy.varto_novomyrgorod.varto.view.Toolbar;

public class MainFragment extends MvpFragment<MainView, MainPresenter>
        implements MainView, View.OnClickListener {
    public static final String TAG = "MainFragment";
    private Toolbar toolbar;
    private LinearLayout newsPlusLayout;
    private LinearLayout sharesPlusLayout;
    private LinearLayout newsDishesLayout;
    private LinearLayout sharesDishesLayout;
    private TextView countNewsPlus;
    private TextView countSharesPlus;
    private TextView countNewsDishes;
    private TextView countSharesDishes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView buttonPlus = (TextView) getActivity().findViewById(R.id.text_varto_plus);
        TextView buttonDishes = (TextView) getActivity().findViewById(R.id.text_varto_dishes);
        buttonPlus.setOnClickListener(this);
        buttonDishes.setOnClickListener(this);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        newsPlusLayout = (LinearLayout) getActivity().findViewById(R.id.layout_fresh_news_plus);
        newsDishesLayout = (LinearLayout) getActivity().findViewById(R.id.layout_fresh_news_varto_dishes);
        sharesPlusLayout = (LinearLayout) getActivity().findViewById(R.id.layout_goods_varto_plus);
        sharesDishesLayout = (LinearLayout) getActivity().findViewById(R.id.layout_goods_varto_dishes);

        countNewsPlus = (TextView) getActivity().findViewById(R.id.text_new_news_plus);
        countSharesPlus = (TextView) getActivity().findViewById(R.id.text_new_goods_plus);
        countNewsDishes = (TextView) getActivity().findViewById(R.id.text_fresh_news_dishes);
        countSharesDishes = (TextView) getActivity().findViewById(R.id.text_new_goods_dishes);

        ImageView imageViewPlus = (ImageView) getActivity().findViewById(R.id.image_shop_varto_plus);
        ImageView imageViewDishes = (ImageView) getActivity().findViewById(R.id.image_varto_dishes);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(400, true, true, true))
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("assets://images/plus.jpg", imageViewPlus, options);
        imageLoader.displayImage("assets://images/dishes.jpg", imageViewDishes, options);
        presenter.onCreate(getArguments());
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setVisibleBackButton(false);
        toolbar.setVisibleLogo(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        toolbar.setVisibleLogo(false);
    }

    @Override
    public void showQuantityContent(Bundle bundle) {
        Animation slide = AnimationUtils.loadAnimation(getContext(),
                R.anim.abc_slide_in_bottom);
        int newsPlus = bundle.getInt(ContentIntentService.EXTRA_NEWS_PLUS);
        int newsDishes = bundle.getInt(ContentIntentService.EXTRA_NEWS_DISHES);
        int goodsPlus = bundle.getInt(ContentIntentService.EXTRA_GOODS_PLUS);
        int goodsDishes = bundle.getInt(ContentIntentService.EXTRA_GOODS_DISHES);

        if (newsPlus > 0) {
            countNewsPlus.setText("(+" + newsPlus + ") новин");
            newsPlusLayout.setVisibility(View.VISIBLE);
            newsPlusLayout.startAnimation(slide);
        }
        if (newsDishes > 0) {
            countNewsDishes.setText("(+" + newsDishes + ") новин");
            newsDishesLayout.setVisibility(View.VISIBLE);
            newsDishesLayout.startAnimation(slide);
        }
        if (goodsPlus > 0) {
            countSharesPlus.setText("(+" + goodsPlus + ") товар");
            sharesPlusLayout.setVisibility(View.VISIBLE);
            sharesPlusLayout.startAnimation(slide);
        }
        if (goodsDishes > 0) {
            countSharesDishes.setText("(+" + goodsDishes + ") товар");
            sharesDishesLayout.setVisibility(View.VISIBLE);
            sharesDishesLayout.startAnimation(slide);
        }
    }

    @Override
    public void hideQuantityContent() {
        newsPlusLayout.setVisibility(View.GONE);
        newsDishesLayout.setVisibility(View.GONE);
        sharesPlusLayout.setVisibility(View.GONE);
        sharesDishesLayout.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.text_varto_plus) {
            presenter.goTo(Shop.PLUS);
        } else if (i == R.id.text_varto_dishes) {
            presenter.goTo(Shop.DISHES);
        }
    }

    @Override
    public void displayMenu(Shop shop) {
        ((ContainerActivity) getActivity()).getNavigationManager().startMenuShop(shop);
    }
}