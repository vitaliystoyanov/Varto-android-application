package es.esy.varto_novomyrgorod.varto.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.pojo.NewsObject;

public class DBNewsProvider extends DBConstants {
    private DBHelper localDBHelper;
    private static final String TAG_LOG = "DBG";

    public DBNewsProvider(DBHelper localDBHelper) {
        this.localDBHelper = localDBHelper;
    }

    public void setNewsToSQLDatabase(List<NewsObject> listNewsToSQLDatabase) {
        if (!listNewsToSQLDatabase.isEmpty()) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(TAG_LOG, "[TABLE: news]SQL:  DBConnect = localDBHelper.getWritableDatabase(): " + DBConnect.toString());

                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE_NEWS, null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        int clearCount = DBConnect.delete(TAG_TABLE_NEWS, null, null);
                        Log.i(TAG_LOG, "[TABLE: news]SQL:  SUCCESS DELETE, delete number of rows: " + clearCount);
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }

                for (int i = 0; i < listNewsToSQLDatabase.size(); i++) {
                    ContentValues contentValues = new ContentValues();
                    NewsObject newsObject = listNewsToSQLDatabase.get(i);

                    contentValues.put(TAG_ID, newsObject.getId());
                    contentValues.put(TAG_SHOP, newsObject.getShop());
                    contentValues.put(TAG_TITLE, newsObject.getTitle());
                    contentValues.put(TAG_IMAGE, newsObject.getImage());
                    contentValues.put(TAG_ARTICLE, newsObject.getArticle());
                    contentValues.put(TAG_CREATED_AT, newsObject.getCreated_at());

                    Log.i(TAG_LOG, "[TABLE: news]SQL:  Result SQL insert operation: "
                            + String.valueOf(DBConnect.insert(TAG_TABLE_NEWS, null, contentValues))
                            + ", NewsObject size of which is transmitted database: "
                            + String.valueOf(contentValues.size()));
                }

            } finally {
                if (DBConnect != null) DBConnect.close();
            }
        }else {
            Log.i(TAG_LOG, "[TABLE: news] List<NewsObject> is empty!");
        }
    }

    public List<NewsObject> getNewsFromSQLDatabase(String shop) {
        ArrayList<NewsObject> newsObject = new ArrayList<>();
        if (shop != null) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(TAG_LOG, "[TABLE: news]SQL:  DBConnect = localDBHelper.getWritableDatabase(): "
                        + DBConnect.toString());

                String whereArgs = "shop = ?";
                String[] whereValues = new String[]{shop};

                Cursor cursor = null;
                try {
                    String orderBy = "id DESC";
                    cursor = DBConnect.query(TAG_TABLE_NEWS, null, whereArgs, whereValues, null, null, orderBy);
                    int idColIndex = cursor.getColumnIndex(TAG_ID);
                    int shopColIndex = cursor.getColumnIndex(TAG_SHOP);
                    int titleColIndex = cursor.getColumnIndex(TAG_TITLE);
                    int articleColIndex = cursor.getColumnIndex(TAG_ARTICLE);
                    int imageColIndex = cursor.getColumnIndex(TAG_IMAGE);
                    int created_atColIndex = cursor.getColumnIndex(TAG_CREATED_AT);

                    if (cursor.moveToFirst()) {
                        do {
                            NewsObject object = new NewsObject();

                            object.setId(cursor.getInt(idColIndex));
                            object.setShop(cursor.getString(shopColIndex));
                            object.setTitle(cursor.getString(titleColIndex));
                            object.setArticle(cursor.getString(articleColIndex));
                            object.setImage(cursor.getString(imageColIndex));
                            object.setCreated_at(cursor.getString(created_atColIndex));

                            newsObject.add(object);
                        } while (cursor.moveToNext());
                        Log.i(TAG_LOG, "[TABLE: news]SQL:  Total objects in the ArrayList<NewsObject>"
                                + ",which will return method: "
                                + newsObject.size());
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            } finally {
                if (DBConnect != null) DBConnect.close();
            }

            return newsObject;
        } else {
            Log.i(TAG_LOG, "[TABLE: news]  getNewsFromSQLDatabase(String shop) -  String shop is empty!");
            return Collections.emptyList();
        }
    }

    public List<Integer> getArrayListID(String shop) {
        List<Integer> newsObject = new ArrayList<Integer>();
        if (shop != null) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(TAG_LOG, "[TABLE: news, getArrayListID]SQL:  DBConnect = localDBHelper.getWritableDatabase(): "
                        + DBConnect.toString());

                String whereArgs = "shop = ?";
                String[] whereValues = new String[]{shop};

                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE_NEWS, null, whereArgs, whereValues, null, null, null);
                    int idColIndex = cursor.getColumnIndex(TAG_ID);

                    if (cursor.moveToFirst()) {
                        do {
                            newsObject.add(cursor.getInt(idColIndex));
                        } while (cursor.moveToNext());
                        Log.i(TAG_LOG, "[TABLE: news, getArrayListID]SQL:  Total objects in the ArrayList<NewsObject>"
                                + ",which will return method: "
                                + newsObject.size());
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            } finally {
                if (DBConnect != null) DBConnect.close();
            }

            return newsObject;
        } else {
            Log.i(TAG_LOG, "[TABLE: news, getArrayListID]  getNewsFromSQLDatabase(String shop) -  String shop is empty!");
            return Collections.emptyList();
        }
    }
}
