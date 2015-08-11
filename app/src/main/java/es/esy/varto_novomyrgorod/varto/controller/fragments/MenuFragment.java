package es.esy.varto_novomyrgorod.varto.controller.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBScheduleProvider;
import es.esy.varto_novomyrgorod.varto.model.pojo.ScheduleObject;

public class MenuFragment extends Fragment implements View.OnClickListener{
    public static final String FROM = "FROM";
    public static final String NEWS = "NEWS";
    public static final String SHARES = "SHARES";
    public static final int DURATION_MILLIS_ANIMATION = 1000;
    private LinearLayout backLayout;
    private ImageView saleImage;
    private ImageView newsImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, null);
    }

    public static MenuFragment newInstance(String source, int news, int shares){
        MenuFragment fragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FROM, source);
        bundle.putInt(NEWS, news);
        bundle.putInt(SHARES, shares);
        fragment.setArguments(bundle);

        return fragment;
    }

    private String getStringFromBundle(String key){
        return getArguments().getString(key);
    }

    @Override
    public void onStart() {
        super.onStart();
        backLayout = (LinearLayout) getActivity().findViewById(R.id.back_layout);
        saleImage = (ImageView) getActivity().findViewById(R.id.imageview_sale);
        newsImage = (ImageView) getActivity().findViewById(R.id.imageview_news);
        TextView textviewMenuNews = (TextView) getActivity().findViewById(R.id.textview_menu_news);
        textviewMenuNews.setOnClickListener(this);
        TextView textviewMenuSale = (TextView) getActivity().findViewById(R.id.textview_menu_shares);
        textviewMenuSale.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        backLayout.setVisibility(View.VISIBLE);
        TextView textViewStatus = (TextView) getActivity().findViewById(R.id.textview_status);
        textViewStatus.setText(R.string.status_location_bar_menu);

        TextView textViewLocation = (TextView) getActivity().findViewById(R.id.textview_location);
        TextView textViewEmail = (TextView) getActivity().findViewById(R.id.textview_email);

        fillingData(textViewLocation, textViewEmail);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(DURATION_MILLIS_ANIMATION, true, true, true))
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("assets://images/sale.jpg", saleImage, options);
        imageLoader.displayImage("assets://images/wine.jpg", newsImage, options);
    }

    private void fillingData(TextView textViewLocation, TextView textViewEmail) {
        TextView textViewTimetable = (TextView) getActivity().findViewById(R.id.textview_timetable);
        String timetable = getResources().getString(R.string.timetable_for_today);

        DBScheduleProvider dbScheduleProvider = new DBScheduleProvider(new DBHelper(getActivity()));
        ScheduleObject object = dbScheduleProvider.getScheduleFromDB(getStringFromBundle(FROM));

        String timetableShop = object.getScheduleToday();
        textViewTimetable.setText(timetable + " " + timetableShop);

        switch (getStringFromBundle(FROM)) {
            case "plus": {
                textViewLocation.setText(R.string.map_location_plus);
                textViewEmail.setText(R.string.email_plus);
            }
            break;
            case "dishes": {
                textViewLocation.setText(R.string.map_location_dishes);
                textViewEmail.setText(R.string.email_dishes);
            }
            break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        backLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        final String from = this.getArguments().getString(FROM);
        switch(v.getId()) {
            case R.id.textview_menu_news: {
                NewsFragment news = NewsFragment.newInstance(from);
                transaction.replace(R.id.container, news).commit();
            }
            break;
            case R.id.textview_menu_shares: {
                CatalogFragment catalog = CatalogFragment.newInstance(from);
                transaction.replace(R.id.container, catalog).commit();
            }
            break;
        }
    }
}