package es.esy.varto_novomyrgorod.varto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import es.esy.varto_novomyrgorod.varto.database.dao.schema.CatalogSchema;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.GoodsSchema;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.NewsSchema;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.QuantitySchema;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.ScheduleSchema;

public class DatabaseHelper extends SQLiteOpenHelper
        implements CatalogSchema, GoodsSchema, ScheduleSchema, NewsSchema, QuantitySchema {

    private static final String TAG = "DatabaseHelper";
    private static final String TAG_DATABASE_DB = "DB";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, TAG_DATABASE_DB, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TAG_TABLE_SCHEDULE + " ("
                + ScheduleSchema.TAG_SHOP + " text,"
                + TAG_SUNDAY + " text,"
                + TAG_MONDAY + " text,"
                + TAG_TUESDAY + " text,"
                + TAG_WEDNESDAY + " text,"
                + TAG_THURSDAY + " text,"
                + TAG_FRIDAY + " text,"
                + TAG_SATURDAY + " text" + ");");
        db.execSQL("create table " + TAG_TABLE_NEWS + " ("
                + NewsSchema.TAG_ID + " int,"
                + NewsSchema.TAG_TITLE + " text,"
                + NewsSchema.TAG_IMAGE + " text,"
                + TAG_ARTICLE + " text,"
                + NewsSchema.TAG_SHOP + " text,"
                + NewsSchema.TAG_CREATED_AT + " text" + ");");
        db.execSQL("create table " + TAG_TABLE_GOODS + " ("
                + GoodsSchema.TAG_ID + " int,"
                + GoodsSchema.TAG_TITLE + " text,"
                + GoodsSchema.TAG_IMAGE + " text,"
                + TAG_CATALOG + " text,"
                + TAG_DESCRIPTION + " text,"
                + GoodsSchema.TAG_SHOP + " text,"
                + TAG_NEW_PRICE + " text,"
                + TAG_OLD_PRICE + " text,"
                + GoodsSchema.TAG_CREATED_AT + " text" + ");");
        db.execSQL("create table " + TAG_TABLE_CATALOG + " ("
                + CatalogSchema.TAG_SHOP + " text,"
                + TAG_NAME + " text" + ");");
        db.execSQL("create table " + TAG_TABLE_QUANTITY + " ("
                + TAG_QUANTITY_NEWS_PLUS + " int,"
                + TAG_QUANTITY_NEWS_DISHES + " int,"
                + TAG_QUANTITY_GOODS_PLUS + " int,"
                + TAG_QUANTITY_GOODS_DISHES + " int" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // FIXME: 6/30/16
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + " which destroys all old data");
    }

    public static void release() {
        if (instance != null) {
            instance.close();
        }
    }
}
