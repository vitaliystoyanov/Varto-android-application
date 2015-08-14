package es.esy.varto_novomyrgorod.varto.controller;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.controller.fragments.MainMenuFragment;
import es.esy.varto_novomyrgorod.varto.model.database.DBCatalogProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBNewsProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBSalesProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBScheduleProvider;
import es.esy.varto_novomyrgorod.varto.model.network.JSONParser;

public class MainFragmentActivity extends FragmentActivity {
    private static final int DURATION_MILLIS = 700;
    private RelativeLayout foreground;
    private ImageButton refresh;
    private LinearLayout newsPlusLayout;
    private LinearLayout sharesPlusLayout;
    private LinearLayout newsDishesLayout;
    private LinearLayout sharesDishesLayout;
    private TextView countNewsPlus;
    private TextView countSharesPlus;
    private TextView countNewsDishes;
    private TextView countSharesDishes;
    private MainMenuFragment menuFragmentInstance;
    private FragmentManager manager;

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
        manager = getSupportFragmentManager();

        newsPlusLayout = (LinearLayout) findViewById(R.id.info_news_plus);
        sharesPlusLayout = (LinearLayout) findViewById(R.id.info_shares_plus);
        newsDishesLayout = (LinearLayout) findViewById(R.id.info_news_dishes);
        sharesDishesLayout = (LinearLayout) findViewById(R.id.info_shares_dishes);

        countNewsPlus = (TextView) findViewById(R.id.textview_count_news_plus);
        countSharesPlus = (TextView) findViewById(R.id.textview_count_shares_plus);
        countNewsDishes = (TextView) findViewById(R.id.textview_count_news_dishes);
        countSharesDishes = (TextView) findViewById(R.id.textview_count_shares_dishes);

        ImageButton back = (ImageButton) findViewById(R.id.button_back);
        //settings = (ImageButton) findViewById(R.id.button_settings);
        foreground = (RelativeLayout) findViewById(R.id.foregroung_panel);
        refresh = (ImageButton) findViewById(R.id.button_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadContentAsyncTask().execute();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.popBackStack();
            }
        });

        menuFragmentInstance = new MainMenuFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, menuFragmentInstance).commit();
        new LoadContentAsyncTask().execute();
    }

    class LoadContentAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
                foreground.setVisibility(View.VISIBLE);
                refresh.setClickable(false);
//              newsPlusLayout.setVisibility(View.INVISIBLE);
//              sharesPlusLayout.setVisibility(View.INVISIBLE);
//              newsDishesLayout.setVisibility(View.INVISIBLE);
//              sharesDishesLayout.setVisibility(View.INVISIBLE);
                animationRefreshButton(true);
        }

        protected Void doInBackground(Void... args) {
            JSONParser parsedObjects = new JSONParser();

            DBHelper dbHelper = new DBHelper(MainFragmentActivity.this);
            DBScheduleProvider dbManager = new DBScheduleProvider(dbHelper);
            DBNewsProvider dbNewsProvider = new DBNewsProvider(dbHelper);
            DBCatalogProvider dbCatalogProvider = new DBCatalogProvider(dbHelper);
            DBSalesProvider dbSalesProvider = new DBSalesProvider(dbHelper);

            dbManager.setScheduleObjectToDB(parsedObjects.getTimetables());
            dbNewsProvider.setNewsToSQLDatabase(parsedObjects.getNews());
            dbCatalogProvider.setCatalogsToSQLDatabase(parsedObjects.getCatalogs());
            dbSalesProvider.setSaleObjectsToSQLDatabase(parsedObjects.getSales());

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            final FragmentTransaction transaction = manager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.replace(R.id.container, menuFragmentInstance).commit();

            foreground.setVisibility(View.INVISIBLE);
            animationRefreshButton(false);
            refresh.setClickable(true);
        }
    }

    private void animationRefreshButton(Boolean state) {
        ImageButton refresh = (ImageButton) findViewById(R.id.button_refresh);
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
}