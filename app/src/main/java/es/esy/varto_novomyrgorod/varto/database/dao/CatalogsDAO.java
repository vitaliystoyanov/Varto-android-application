package es.esy.varto_novomyrgorod.varto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.database.DatabaseProvider;
import es.esy.varto_novomyrgorod.varto.database.dao.interfaces.DAOInterface;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.CatalogSchema;
import es.esy.varto_novomyrgorod.varto.pojo.Catalog;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public class CatalogsDAO implements DAOInterface<Catalog>, CatalogSchema {
    private static final String TAG = "CatalogsDAO";
    private final Context context;

    public CatalogsDAO(Context context) {
        this.context = context;
    }

    @Override
    public HashMap<Shop, Integer> deleteAndAdd(List<Catalog> catalogs) {
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context).query(TAG_TABLE_CATALOG,
                    null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int rowsAffected = DatabaseProvider.getInstance(context)
                        .delete(TAG_TABLE_CATALOG, null, null);
                Log.i(TAG, "[TABLE: catalog]SQL:  SUCCESS DELETE, deleted number of rows: "
                        + rowsAffected);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        for (int i = 0; i < catalogs.size(); i++) {
            ContentValues contentValues = new ContentValues();
            Catalog item = catalogs.get(i);
            contentValues.put(TAG_SHOP, item.getShop());
            contentValues.put(TAG_NAME, item.getName());
            Log.i(TAG, "[TABLE: catalog]SQL:  Result SQL insert operation: "
                    + String.valueOf(DatabaseProvider.getInstance(context)
                    .insert(TAG_TABLE_CATALOG, null, contentValues))
                    + ", Catalog size of which is transmitted database: "
                    + String.valueOf(contentValues.size()));
        }
        return null;
    }

    @Override
    public List<Catalog> getAll(Shop shop) {
        List<Catalog> catalogs = new ArrayList<>();
        String whereArgs = "shop = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase()};
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context)
                    .query(TAG_TABLE_CATALOG, null, whereArgs, whereValues, null, null, null);
            int shopColIndex = cursor.getColumnIndex(TAG_SHOP);
            int nameColIndex = cursor.getColumnIndex(TAG_NAME);
            if (cursor.moveToFirst()) {
                do {
                    Catalog item = new Catalog();
                    item.setName(cursor.getString(nameColIndex));
                    item.setShop(cursor.getString(shopColIndex));
                    catalogs.add(item);
                } while (cursor.moveToNext());
                Log.i(TAG, "[TABLE: catalog]SQL:  Total objects in the ArrayList<Catalog>"
                        + ",which will return method getCatalogsFromSQLDatabase - "
                        + catalogs.size());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return catalogs;
    }
}
