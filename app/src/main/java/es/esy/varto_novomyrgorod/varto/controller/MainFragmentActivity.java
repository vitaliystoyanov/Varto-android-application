package es.esy.varto_novomyrgorod.varto.controller;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.controller.fragments.MainMenuFragment;
import es.esy.varto_novomyrgorod.varto.model.database.DBCatalogsProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBInfomationProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBNewsProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBGoodsProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBScheduleProvider;
import es.esy.varto_novomyrgorod.varto.model.network.JSONParser;
import es.esy.varto_novomyrgorod.varto.model.pojo.InformationObject;

public class MainFragmentActivity extends FragmentActivity {
    private static final int DURATION_MILLIS = 700;
    private static final String FIRST_SHOP = "plus";
    private static final String SECOND_SHOP = "dishes";
    private RelativeLayout foreground;
    private RelativeLayout container;
    private LinearLayout refresh;
    private FragmentManager manager;
    private MainMenuFragment menuFragmentInstance;
    private NotificationManager notificationManager;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        container = (RelativeLayout) findViewById(R.id.container);
        menuFragmentInstance = new MainMenuFragment();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, menuFragmentInstance).commit();
        //menuFragmentInstance.hideAllInforamation();

        notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        //settings = (ImageButton) findViewById(R.id.button_settings);
        foreground = (RelativeLayout) findViewById(R.id.foregroung_panel);
        refresh = (LinearLayout) findViewById(R.id.button_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadContentAsyncTask().execute();
            }
        });
        LinearLayout back = (LinearLayout) findViewById(R.id.back_layout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.popBackStack();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    class LoadContentAsyncTask extends AsyncTask<Void, Integer, InformationObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            final FragmentTransaction transaction = manager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.replace(R.id.container, menuFragmentInstance).commit();
            enableDisableViewGroup(container, false);

            menuFragmentInstance.hideAllInforamation();
            foreground.setVisibility(View.VISIBLE);
            animationRefreshButton(true);
            refresh.setClickable(false);

        }

        protected InformationObject doInBackground(Void... args) {
            InformationObject informationObject = new InformationObject();
            JSONParser parsedObjects = new JSONParser();

            DBHelper dbHelper = new DBHelper(MainFragmentActivity.this);
            DBScheduleProvider dbManager = new DBScheduleProvider(dbHelper);
            DBNewsProvider dbNewsProvider = new DBNewsProvider(dbHelper);
            DBCatalogsProvider dbCatalogsProvider = new DBCatalogsProvider(dbHelper);
            DBGoodsProvider dbGoodsProvider = new DBGoodsProvider(dbHelper);

            dbManager.setScheduleToSQLDataBase(parsedObjects.getTimetables());
            dbCatalogsProvider.setCatalogsToSQLDatabase(parsedObjects.getCatalogs());

            List<Integer> oldListPlus = dbNewsProvider.getArrayListID(FIRST_SHOP);
            List<Integer> oldListDishes = dbNewsProvider.getArrayListID(SECOND_SHOP);
            dbNewsProvider.setNewsToSQLDatabase(parsedObjects.getNews());
            List<Integer> newListPlus = dbNewsProvider.getArrayListID(FIRST_SHOP);
            List<Integer> newListDishes = dbNewsProvider.getArrayListID(SECOND_SHOP);
            newListPlus.removeAll(oldListPlus);
            newListDishes.removeAll(oldListDishes);

            informationObject.setAmountOfNewsPlus(newListPlus.size());
            informationObject.setAmountOfNewsDishes(newListDishes.size());

            List<Integer> oldListGoodsPlus = dbGoodsProvider.getArrayListID(FIRST_SHOP);
            List<Integer> oldListGoodsDishes = dbGoodsProvider.getArrayListID(SECOND_SHOP);
            dbGoodsProvider.setSaleObjectsToSQLDatabase(parsedObjects.getSales());
            List<Integer> newListGoodsPlus = dbGoodsProvider.getArrayListID(FIRST_SHOP);
            List<Integer> newListGoodsDishes = dbGoodsProvider.getArrayListID(SECOND_SHOP);
            newListGoodsPlus.removeAll(oldListGoodsPlus);
            newListGoodsDishes.removeAll(oldListGoodsDishes);

            informationObject.setAmountOfGoodsPlus(newListGoodsPlus.size());
            informationObject.setAmountOfGoodsDishes(newListGoodsDishes.size());

            DBInfomationProvider dbInfomationProvider = new DBInfomationProvider(dbHelper);
            dbInfomationProvider.setInformationToSQLDatabase(informationObject);
            return informationObject;
        }

        @Override
        protected void onPostExecute(InformationObject result) {
            menuFragmentInstance.showInformation(result);

            foreground.setVisibility(View.INVISIBLE);
            animationRefreshButton(false);
            refresh.setClickable(true);
            enableDisableViewGroup(container, true);
        }
    }

    private void animationRefreshButton(Boolean state) {
        ImageView refresh = (ImageView) findViewById(R.id.imageview_refresh);
        if (state == true) {
            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(DURATION_MILLIS);
            refresh.startAnimation(anim);
        } else {
            refresh.setAnimation(null);
        }
    }

    private static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }
}