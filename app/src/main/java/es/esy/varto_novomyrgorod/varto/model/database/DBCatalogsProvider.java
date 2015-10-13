package es.esy.varto_novomyrgorod.varto.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;

public class DBCatalogsProvider extends DBConstants {
    private DBHelper localDBHelper;
    private static final String TAG_LOG = "DBG";

    public DBCatalogsProvider(DBHelper localDBHelper) {
        this.localDBHelper = localDBHelper;
    }

    public void setCatalogsToSQLDatabase(List<CatalogObject> catalogs) {
        if (!catalogs.isEmpty()) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();

                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE_CATALOG, null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        int clearCount = DBConnect.delete(TAG_TABLE_CATALOG, null, null);
                        Log.i(TAG_LOG, "[TABLE: catalog]SQL:  SUCCESS DELETE, delete number of rows: " + clearCount);
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }

                for (int i = 0; i < catalogs.size(); i++) {
                    ContentValues contentValues = new ContentValues();
                    CatalogObject object = catalogs.get(i);

                    contentValues.put(TAG_SHOP, object.getShop());
                    contentValues.put(TAG_NAME, object.getName());

                    Log.i(TAG_LOG, "[TABLE: catalog]SQL:  Result SQL insert operation: "
                            + String.valueOf(DBConnect.insert(TAG_TABLE_CATALOG, null, contentValues))
                            + ", CatalogObject size of which is transmitted database: "
                            + String.valueOf(contentValues.size()));
                }

            } finally {
                if (DBConnect != null) DBConnect.close();
            }
        } else
            Log.i(TAG_LOG, "[TABLE: catalog] List<CatalogObject> is empty!");
    }

    public List<CatalogObject> getCatalogsFromSQLDatabase(String shop) {
        if (shop != null) {
            ArrayList<CatalogObject> catalogObjects = new ArrayList<>();
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();

                String whereArgs = "shop = ?";
                String[] whereValues = new String[]{shop};
                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE_CATALOG, null, whereArgs, whereValues, null, null, null);
                    int shopColIndex = cursor.getColumnIndex(TAG_SHOP);
                    int nameColIndex = cursor.getColumnIndex(TAG_NAME);

                    if (cursor.moveToFirst()) {
                        do {
                            CatalogObject object = new CatalogObject();

                            object.setName(cursor.getString(nameColIndex));
                            object.setShop(cursor.getString(shopColIndex));
                            catalogObjects.add(object);
                        } while (cursor.moveToNext());
                        Log.i(TAG_LOG, "[TABLE: catalog]SQL:  Total objects in the ArrayList<CatalogObject>"
                                + ",which will return method getCatalogsFromSQLDatabase - "
                                + catalogObjects.size());
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            } finally {
                if (DBConnect != null) DBConnect.close();
            }
            return catalogObjects;
        } else {
            Log.i(TAG_LOG, "[TABLE: news]  getNewsFromSQLDatabase(String shop) -  arg shop is empty!");
            return Collections.emptyList();
        }
    }
}
