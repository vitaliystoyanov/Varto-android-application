package es.esy.varto_novomyrgorod.varto.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.model.database.DBCatalogProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBInfomationProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBNewsProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBSalesProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBScheduleProvider;
import es.esy.varto_novomyrgorod.varto.model.network.JSONParser;
import es.esy.varto_novomyrgorod.varto.model.pojo.InformationObject;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        new LoadingDataFromServer().execute();
    }

    class LoadingDataFromServer extends AsyncTask<Void, Void, InformationObject> {

        @Override
        protected InformationObject doInBackground(Void... params) {
            InformationObject informationObject = new InformationObject();
            JSONParser parsedObjects = new JSONParser();

            DBHelper dbHelper = new DBHelper(SplashActivity.this);
            DBScheduleProvider dbManager = new DBScheduleProvider(dbHelper);
            DBNewsProvider dbNewsProvider = new DBNewsProvider(dbHelper);
            DBCatalogProvider dbCatalogProvider = new DBCatalogProvider(dbHelper);
            DBSalesProvider dbSalesProvider = new DBSalesProvider(dbHelper);

            dbManager.setScheduleObjectToDB(parsedObjects.getTimetables());
            dbCatalogProvider.setCatalogsToSQLDatabase(parsedObjects.getCatalogs());

            List<Integer> oldListPlus = dbNewsProvider.getArrayListID("plus");
            List<Integer> oldListDishes = dbNewsProvider.getArrayListID("dishes");
            dbNewsProvider.setNewsToSQLDatabase(parsedObjects.getNews());
            List<Integer> newListPlus = dbNewsProvider.getArrayListID("plus");
            List<Integer> newListDishes = dbNewsProvider.getArrayListID("dishes");
            newListPlus.removeAll(oldListPlus);
            newListDishes.removeAll(oldListDishes);

            informationObject.setAmountOfNewsPlus(newListPlus.size());
            informationObject.setAmountOfNewsDishes(newListDishes.size());

            List<Integer> oldListGoodsPlus = dbSalesProvider.getArrayListID("plus");
            List<Integer> oldListGoodsDishes = dbSalesProvider.getArrayListID("dishes");
            dbSalesProvider.setSaleObjectsToSQLDatabase(parsedObjects.getSales());
            List<Integer> newListGoodsPlus = dbSalesProvider.getArrayListID("plus");
            List<Integer> newListGoodsDishes = dbSalesProvider.getArrayListID("dishes");
            newListGoodsPlus.removeAll(oldListGoodsPlus);
            newListGoodsDishes.removeAll(oldListGoodsDishes);

            informationObject.setAmountOfGoodsPlus(newListGoodsPlus.size());
            informationObject.setAmountOfGoodsDishes(newListGoodsDishes.size());

            DBInfomationProvider dbInfomationProvider = new DBInfomationProvider(dbHelper);
            dbInfomationProvider.setInformationToSQLDatabase(informationObject);
            return null;
        }

        @Override
        protected void onPostExecute(InformationObject result) {
            Intent intentSwithing = new Intent(SplashActivity.this, MainFragmentActivity.class);
            startActivity(intentSwithing);
        }
    }
}