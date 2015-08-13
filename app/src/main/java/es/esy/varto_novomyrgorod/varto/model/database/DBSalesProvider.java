package es.esy.varto_novomyrgorod.varto.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.pojo.SaleObject;

public class DBSalesProvider extends DBConstants{
    private DBHelper localDBHelper;
    private static final String TAG_LOG = "DBG";

    public DBSalesProvider(DBHelper localDBHelper) {
        this.localDBHelper = localDBHelper;
    }

    public void setSaleObjectsToSQLDatabase(List<SaleObject> saleObjectListToSQLDatabase) {
        if (!saleObjectListToSQLDatabase.isEmpty()) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(TAG_LOG, "[TABLE: sale]SQL:  DBConnect = localDBHelper.getWritableDatabase(): " + DBConnect.toString());

                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE_SALES, null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        int clearCount = DBConnect.delete(TAG_TABLE_SALES, null, null);
                        Log.i(TAG_LOG, "[TABLE: sale]SQL:  SUCCESS DELETE, delete number of rows: " + clearCount);
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }

                for (int i = 0; i < saleObjectListToSQLDatabase.size(); i++) {
                    ContentValues contentValues = new ContentValues();
                    SaleObject saleObject = saleObjectListToSQLDatabase.get(i);

                    contentValues.put(TAG_ID, saleObject.getId());
                    contentValues.put(TAG_SHOP, saleObject.getShop());
                    contentValues.put(TAG_TITLE, saleObject.getTitle());
                    contentValues.put(TAG_CATALOG, saleObject.getCatalog());
                    contentValues.put(TAG_IMAGE, saleObject.getImage());
                    contentValues.put(TAG_NEW_PRICE, saleObject.getNew_price());
                    contentValues.put(TAG_OLD_PRICE, saleObject.getOld_price());
                    contentValues.put(TAG_DESCRIPTION, saleObject.getDescription());
                    contentValues.put(TAG_CREATED_AT, saleObject.getCreated_at());

                    Log.i(TAG_LOG, "[TABLE: sale]SQL:  Result SQL insert operation: "
                            + String.valueOf(DBConnect.insert(TAG_TABLE_SALES, null, contentValues))
                            + ", SaleObject size of which is transmitted database: "
                            + String.valueOf(contentValues.size()));
                }

            } finally {
                if (DBConnect != null) DBConnect.close();
            }
        }else {
            Log.i(TAG_LOG, "[TABLE: sale] List<SaleObject> is empty!");
        }
    }

    public List<SaleObject> getSalesObjectsFromSQLDatabase(String shop, String catalog) {
        ArrayList<SaleObject> salesObject = new ArrayList<>();
        if ((shop != null) && (catalog != null)) {
            SQLiteDatabase DBConnect = null;
            try {
                DBConnect = localDBHelper.getWritableDatabase();
                Log.i(TAG_LOG, "[TABLE: sale]SQL:  DBConnect = localDBHelper.getWritableDatabase(): "
                        + DBConnect.toString());

                String whereArgs = "shop = ? AND catalog = ?";
                String[] whereValues = new String[]{shop, catalog};

                Cursor cursor = null;
                try {
                    cursor = DBConnect.query(TAG_TABLE_SALES, null, whereArgs, whereValues, null, null, null);
                    int idColIndex = cursor.getColumnIndex(TAG_ID);
                    int shopColIndex = cursor.getColumnIndex(TAG_SHOP);
                    int titleColIndex = cursor.getColumnIndex(TAG_TITLE);
                    int catalogColIndex = cursor.getColumnIndex(TAG_CATALOG);
                    int descriptionColIndex = cursor.getColumnIndex(TAG_DESCRIPTION);
                    int imageColIndex = cursor.getColumnIndex(TAG_IMAGE);
                    int new_priceColIndex = cursor.getColumnIndex(TAG_NEW_PRICE);
                    int old_priceColIndex = cursor.getColumnIndex(TAG_OLD_PRICE);
                    int created_atColIndex = cursor.getColumnIndex(TAG_CREATED_AT);

                    if (cursor.moveToFirst()) {
                        do {
                            SaleObject object = new SaleObject();

                            object.setId(cursor.getInt(idColIndex));
                            object.setShop(cursor.getString(shopColIndex));
                            object.setTitle(cursor.getString(titleColIndex));
                            object.setCatalog(cursor.getString(catalogColIndex));
                            object.setDescription(cursor.getString(descriptionColIndex));
                            object.setImage(cursor.getString(imageColIndex));
                            object.setNew_price(cursor.getString(new_priceColIndex));
                            object.setOld_price(cursor.getString(old_priceColIndex));
                            object.setCreated_at(cursor.getString(created_atColIndex));

                              salesObject.add(object);
                        } while (cursor.moveToNext());
                        Log.i(TAG_LOG, "[TABLE: sale]SQL:  Total objects in the ArrayList<SaleObject>"
                                + ", which will return method: "
                                + salesObject.size());
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            } finally {
                if (DBConnect != null) DBConnect.close();
            }

            return salesObject;
        } else {
            Log.i(TAG_LOG, "[TABLE: sale]  getNewsFromSQLDatabase(String shop) -  String shop is empty!");
            return Collections.emptyList();
        }
    }
}