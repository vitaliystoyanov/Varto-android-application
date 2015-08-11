package es.esy.varto_novomyrgorod.varto.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vitaliy Stoyanov on 8/11/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBG", "onCreate database");
        db.execSQL("create table timetable ("
                + "shop text,"
                + "sunday text,"
                + "monday text,"
                + "tuesday text,"
                + "wednesday text,"
                + "thursday text,"
                + "friday text,"
                + "saturday text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
