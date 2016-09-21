package es.esy.varto_novomyrgorod.varto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {

    private static SQLiteDatabase instance;

    public static synchronized SQLiteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = DatabaseHelper.getInstance(context).getWritableDatabase();
        }
        return instance;
    }

    public static void close() {
        if (instance != null) {
            instance.close();
            instance = null;
            DatabaseHelper.release();
        }
    }
}
