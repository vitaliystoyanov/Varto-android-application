package es.esy.varto_novomyrgorod.varto.controller.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

import es.esy.varto_novomyrgorod.varto.R;


public class MainMenuFragment extends Fragment implements View.OnClickListener {
    private LinearLayout logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        logo = (LinearLayout) getActivity().findViewById(R.id.logo_layout);
        TextView buttonPlus = (TextView) getActivity().findViewById(R.id.textview_plus);
        TextView buttonDishes = (TextView) getActivity().findViewById(R.id.textview_dishes);
        buttonPlus.setOnClickListener(this);
        buttonDishes.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        logo.setVisibility(View.VISIBLE);
        ImageView imageViewPlus = (ImageView) getActivity().findViewById(R.id.imageview_plus);
        ImageView imageViewDishes = (ImageView) getActivity().findViewById(R.id.imageview_dishes);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(1000, true,true,true))
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("assets://images/designer.jpeg", imageViewPlus, options);
        imageLoader.displayImage("assets://images/fashion.jpeg", imageViewDishes, options);
    }

    @Override
    public void onPause() {
        super.onPause();
        logo.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        switch (v.getId()) {
            case R.id.textview_plus: {
                MenuFragment menuFragment = MenuFragment.newInstance("plus",
                        0,
                        0);
                transaction.replace(R.id.container, menuFragment).commit();
            }
            break;
            case R.id.textview_dishes: {
                MenuFragment menuFragment = MenuFragment.newInstance("dishes",
                        0,
                        0);
                transaction.replace(R.id.container, menuFragment).commit();
            }
            break;
        }
    }
}