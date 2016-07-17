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
    public HashMap<Shop, Integer> add(List<Catalog> listOfItems) {
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context).query(TAG_TABLE_CATALOG,
                    null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int clearCount = DatabaseProvider.getInstance(context).delete(TAG_TABLE_CATALOG, null, null);
                Log.i(TAG, "[TABLE: catalog]SQL:  SUCCESS DELETE, delete number of rows: " + clearCount);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        for (int i = 0; i < listOfItems.size(); i++) {
            ContentValues contentValues = new ContentValues();
            Catalog object = listOfItems.get(i);
            contentValues.put(TAG_SHOP, object.getShop());
            contentValues.put(TAG_NAME, object.getName());
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
        List<Catalog> listOfItems = new ArrayList<>();
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
                    Catalog object = new Catalog();
                    object.setName(cursor.getString(nameColIndex));
                    object.setShop(cursor.getString(shopColIndex));
                    listOfItems.add(object);
                } while (cursor.moveToNext());
                Log.i(TAG, "[TABLE: catalog]SQL:  Total objects in the ArrayList<Catalog>"
                        + ",which will return method getCatalogsFromSQLDatabase - "
                        + listOfItems.size());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return listOfItems;
    }
}
