package es.esy.varto_novomyrgorod.varto.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.InformationObject;

public class DBInfomationProvider extends DBConstants {
    private DBHelper localDBHelper;
    private static final String TAG_LOG = "DBG";

    public DBInfomationProvider(DBHelper localDBHelper) {
        this.localDBHelper = localDBHelper;
    }

    public void setInformationToSQLDatabase(InformationObject informationToSQLDatabase) {
        if (informationToSQLDatabase != null) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(TAG_LOG, "[TABLE: information]SQL:  DBConnect = localDBHelper.getWritableDatabase(): " + DBConnect.toString());

                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE_INFORMATION, null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        int clearCount = DBConnect.delete(TAG_TABLE_INFORMATION, null, null);
                        Log.i(TAG_LOG, "[TABLE: inforamation]SQL:  SUCCESS DELETE, delete number of rows: " + clearCount);
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put(TAG_AMOUNT_NEWS_PLUS, informationToSQLDatabase.getAmountOfNewsPlus());
                contentValues.put(TAG_AMOUNT_NEWS_DISHES, informationToSQLDatabase.getAmountOfNewsDishes());
                contentValues.put(TAG_AMOUNT_GOODS_PLUS, informationToSQLDatabase.getAmountOfGoodsPlus());
                contentValues.put(TAG_AMOUNT_GOODS_DISHES, informationToSQLDatabase.getAmountOfGoodsDishes());

                Log.i(TAG_LOG, "news plus = " + informationToSQLDatabase.getAmountOfNewsPlus() +
                        ", news dishes = " + informationToSQLDatabase.getAmountOfNewsDishes() +
                        ", goods plus = " + informationToSQLDatabase.getAmountOfGoodsPlus() + ", goods dishes = " +
                        informationToSQLDatabase.getAmountOfGoodsDishes());
                Log.i(TAG_LOG, "[TABLE: inforamation]SQL:  Result SQL insert operation: "
                        + String.valueOf(DBConnect.insert(TAG_TABLE_INFORMATION, null, contentValues)));
            } finally {
                if (DBConnect != null) DBConnect.close();
            }
        } else {
            Log.i(TAG_LOG, "[TABLE: inforamation] arg informationToSQLDatabase is empty!");
        }
    }

    public InformationObject getInformationFromSQLDatabase() {
        InformationObject informationObject = new InformationObject();
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(TAG_LOG, "[TABLE: inforamtion]SQL:  DBConnect = localDBHelper.getWritableDatabase(): "
                        + DBConnect.toString());

                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE_INFORMATION, null, null, null, null, null, null);
                    int newsPlusColIndex = cursor.getColumnIndex(TAG_AMOUNT_NEWS_PLUS);
                    int newsDishesColIndex = cursor.getColumnIndex(TAG_AMOUNT_NEWS_DISHES);
                    int goodsPlusColIndex = cursor.getColumnIndex(TAG_AMOUNT_GOODS_PLUS);
                    int goodsDishesColIndex = cursor.getColumnIndex(TAG_AMOUNT_GOODS_DISHES);

                    if (cursor.moveToFirst()) {
                        do {
                            informationObject.setAmountOfNewsPlus(cursor.getInt(newsPlusColIndex));
                            informationObject.setAmountOfNewsDishes(cursor.getInt(newsDishesColIndex));
                            informationObject.setAmountOfGoodsPlus(cursor.getInt(goodsPlusColIndex));
                            informationObject.setAmountOfGoodsDishes(cursor.getInt(goodsDishesColIndex));
                        } while (cursor.moveToNext());

                        Log.i(TAG_LOG, "news plus = " + informationObject.getAmountOfNewsPlus() +
                        ", news dishes = " + informationObject.getAmountOfNewsDishes() +
                        ", goods plus = " +informationObject.getAmountOfGoodsPlus() + ", goods dishes = " +
                        informationObject.getAmountOfGoodsDishes());
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            } finally {
                if (DBConnect != null) DBConnect.close();
            }
            return informationObject;
    }
}
