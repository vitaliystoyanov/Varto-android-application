package es.esy.varto_novomyrgorod.varto.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;

public class DBCatalogProvider extends DBConstants {
    private DBHelper localDBHelper;
    private static final String TAG_LOG = "DBG";

    public DBCatalogProvider(DBHelper localDBHelper) {
        this.localDBHelper = localDBHelper;
    }

    public void setCatalogsToSQLDatabase(List<CatalogObject> catalogsToSQLDatabase) {
        if (!catalogsToSQLDatabase.isEmpty()) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(TAG_LOG, "[TABLE: table]SQL:  DBConnect = localDBHelper.getWritableDatabase(): " + DBConnect.toString());

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

                for (int i = 0; i < catalogsToSQLDatabase.size(); i++) {
                    ContentValues contentValues = new ContentValues();
                    CatalogObject catalogObject = catalogsToSQLDatabase.get(i);

                    contentValues.put(TAG_SHOP, catalogObject.getShop());
                    contentValues.put(TAG_NAME, catalogObject.getShop());

                    Log.i(TAG_LOG, "[TABLE: catalog]SQL:  Result SQL insert operation: "
                            + String.valueOf(DBConnect.insert(TAG_TABLE_CATALOG, null, contentValues))
                            + ", CatalogObject size of which is transmitted database: "
                            + String.valueOf(contentValues.size()));
                }

            } finally {
                if (DBConnect != null) DBConnect.close();
            }
        } else {
            Log.i(TAG_LOG, "[TABLE: catalog] List<CatalogObject> is empty!");
        }
    }

    public List<CatalogObject> getCatalogsFromSQLDatabase(String shop) {
        ArrayList<CatalogObject> catalogObjects = new ArrayList<>();
        if (shop != null) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(TAG_LOG, "[TABLE: catalog]SQL:  DBConnect = localDBHelper.getWritableDatabase(): "
                        + DBConnect.toString());

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
                                + ",which will return method: "
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
            Log.i(TAG_LOG, "[TABLE: news]  getNewsFromSQLDatabase(String shop) -  String shop is empty!");
            return Collections.emptyList();
        }
    }
}
