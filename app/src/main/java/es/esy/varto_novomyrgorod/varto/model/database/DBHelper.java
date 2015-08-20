package es.esy.varto_novomyrgorod.varto.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG_DATABASE_DB = "DB";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, TAG_DATABASE_DB, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBG", "onCreate database");
        db.execSQL("create table " + DBConstants.TAG_TABLE_SCHEDULE + " ("
                + DBConstants.TAG_SHOP + " text,"
                + DBConstants.TAG_SUNDAY + " text,"
                + DBConstants.TAG_MONDAY + " text,"
                + DBConstants.TAG_TUESDAY + " text,"
                + DBConstants.TAG_WEDNESDAY + " text,"
                + DBConstants.TAG_THURSDAY + " text,"
                + DBConstants.TAG_FRIDAY + " text,"
                + DBConstants.TAG_SATURDAY + " text" + ");");
        db.execSQL("create table " + DBConstants.TAG_TABLE_NEWS + " ("
                + DBConstants.TAG_ID + " int,"
                + DBConstants.TAG_TITLE + " text,"
                + DBConstants.TAG_IMAGE + " text,"
                + DBConstants.TAG_ARTICLE + " text,"
                + DBConstants.TAG_SHOP + " text,"
                + DBConstants.TAG_CREATED_AT + " text" + ");");
        db.execSQL("create table " + DBConstants.TAG_TABLE_SALES + " ("
                + DBConstants.TAG_ID + " int,"
                + DBConstants.TAG_TITLE + " text,"
                + DBConstants.TAG_IMAGE + " text,"
                + DBConstants.TAG_CATALOG + " text,"
                + DBConstants.TAG_DESCRIPTION + " text,"
                + DBConstants.TAG_SHOP + " text,"
                + DBConstants.TAG_NEW_PRICE + " text,"
                + DBConstants.TAG_OLD_PRICE + " text,"
                + DBConstants.TAG_CREATED_AT + " text" + ");");
        db.execSQL("create table " + DBConstants.TAG_TABLE_CATALOG + " ("
                + DBConstants.TAG_SHOP + " text,"
                + DBConstants.TAG_NAME + " text" + ");");
        db.execSQL("create table " + DBConstants.TAG_TABLE_INFORMATION + " ("
                + DBConstants.TAG_AMOUNT_NEWS_PLUS + " int,"
                + DBConstants.TAG_AMOUNT_NEWS_DISHES + " int,"
                + DBConstants.TAG_AMOUNT_GOODS_PLUS + " int,"
                + DBConstants.TAG_AMOUNT_GOODS_DISHES + " int" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
