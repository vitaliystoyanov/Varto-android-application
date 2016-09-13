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

import java.util.Locale;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.activity.ContainerActivity;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;
import es.esy.varto_novomyrgorod.varto.presenters.MainPresenter;
import es.esy.varto_novomyrgorod.varto.service.ContentIntentService;
import es.esy.varto_novomyrgorod.varto.view.MainView;
import es.esy.varto_novomyrgorod.varto.common.Toolbar;

public class MainFragment extends MvpFragment<MainView, MainPresenter>
        implements MainView, View.OnClickListener {
    public static final String TAG = "MainFragment";
    private Toolbar toolbar;
    private LinearLayout newsPlusLayout;
    private LinearLayout newsDishesLayout;
    private LinearLayout goodsPlusLayout;
    private LinearLayout goodsDishesLayout;
    private TextView quantityNewsPlus;
    private TextView quantityGoodsPlus;
    private TextView quantityNewsDishes;
    private TextView quantityGoodsDishes;

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
        goodsPlusLayout = (LinearLayout) getActivity().findViewById(R.id.layout_goods_varto_plus);
        goodsDishesLayout = (LinearLayout) getActivity().findViewById(R.id.layout_goods_varto_dishes);

        quantityNewsPlus = (TextView) getActivity().findViewById(R.id.text_new_news_plus);
        quantityGoodsPlus = (TextView) getActivity().findViewById(R.id.text_new_goods_plus);
        quantityNewsDishes = (TextView) getActivity().findViewById(R.id.text_fresh_news_dishes);
        quantityGoodsDishes = (TextView) getActivity().findViewById(R.id.text_new_goods_dishes);

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
            quantityNewsPlus.setText(
                    String.format(Locale.getDefault(),
                            "(+%d) %s", newsPlus, getString(R.string.quantity_news_text)));
            newsPlusLayout.setVisibility(View.VISIBLE);
            newsPlusLayout.startAnimation(slide);
        }
        if (newsDishes > 0) {
            quantityNewsDishes.setText(
                    String.format(Locale.getDefault(),
                            "(+%d) %s", newsDishes, getString(R.string.quantity_news_text)));
            newsDishesLayout.setVisibility(View.VISIBLE);
            newsDishesLayout.startAnimation(slide);
        }
        if (goodsPlus > 0) {
            quantityGoodsPlus.setText(
                    String.format(Locale.getDefault(),
                            "(+%d) %s", goodsPlus, getString(R.string.quantity_goods_text)));
            goodsPlusLayout.setVisibility(View.VISIBLE);
            goodsPlusLayout.startAnimation(slide);
        }
        if (goodsDishes > 0) {
            quantityGoodsDishes.setText(
                    String.format(Locale.getDefault(),
                            "(+%d) %s", goodsDishes, getString(R.string.quantity_goods_text)));
            goodsDishesLayout.setVisibility(View.VISIBLE);
            goodsDishesLayout.startAnimation(slide);
        }
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void hideQuantityContent() {
        newsPlusLayout.setVisibility(View.GONE);
        newsDishesLayout.setVisibility(View.GONE);
        goodsPlusLayout.setVisibility(View.GONE);
        goodsDishesLayout.setVisibility(View.GONE);
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