package es.esy.varto_novomyrgorod.varto.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.model.database.DBCatalogsProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBGoodsProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBInfomationProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBNewsProvider;
import es.esy.varto_novomyrgorod.varto.model.database.DBScheduleProvider;
import es.esy.varto_novomyrgorod.varto.model.network.JSONParser;
import es.esy.varto_novomyrgorod.varto.model.pojo.InformationObject;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new LoadingDataFromServer().execute();
    }

    class LoadingDataFromServer extends AsyncTask<Void, Void, InformationObject> {

        @Override
        protected InformationObject doInBackground(Void... params) {
            InformationObject informationObject = new InformationObject();
            JSONParser parsedObjects = new JSONParser();

            DBHelper dbHelper = new DBHelper(SplashScreenActivity.this);
            DBScheduleProvider dbManager = new DBScheduleProvider(dbHelper);
            DBNewsProvider dbNewsProvider = new DBNewsProvider(dbHelper);
            DBCatalogsProvider dbCatalogsProvider = new DBCatalogsProvider(dbHelper);
            DBGoodsProvider dbGoodsProvider = new DBGoodsProvider(dbHelper);

            dbManager.setScheduleToSQLDataBase(parsedObjects.getTimetables());
            dbCatalogsProvider.setCatalogsToSQLDatabase(parsedObjects.getCatalogs());

            List<Integer> oldListPlus = dbNewsProvider.getArrayListID("plus");
            List<Integer> oldListDishes = dbNewsProvider.getArrayListID("dishes");
            dbNewsProvider.setNewsToSQLDatabase(parsedObjects.getNews());
            List<Integer> newListPlus = dbNewsProvider.getArrayListID("plus");
            List<Integer> newListDishes = dbNewsProvider.getArrayListID("dishes");
            newListPlus.removeAll(oldListPlus);
            newListDishes.removeAll(oldListDishes);

            informationObject.setAmountOfNewsPlus(newListPlus.size());
            informationObject.setAmountOfNewsDishes(newListDishes.size());

            List<Integer> oldListGoodsPlus = dbGoodsProvider.getArrayListID("plus");
            List<Integer> oldListGoodsDishes = dbGoodsProvider.getArrayListID("dishes");
            dbGoodsProvider.setSaleObjectsToSQLDatabase(parsedObjects.getSales());
            List<Integer> newListGoodsPlus = dbGoodsProvider.getArrayListID("plus");
            List<Integer> newListGoodsDishes = dbGoodsProvider.getArrayListID("dishes");
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
            Intent intentSwithing = new Intent(SplashScreenActivity.this, MainFragmentActivity.class);
            startActivity(intentSwithing);
        }
    }
}