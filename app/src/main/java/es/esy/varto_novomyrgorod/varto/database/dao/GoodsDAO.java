package es.esy.varto_novomyrgorod.varto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.database.DatabaseProvider;
import es.esy.varto_novomyrgorod.varto.database.dao.interfaces.GoodsDAOInterface;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.GoodsSchema;
import es.esy.varto_novomyrgorod.varto.pojo.Good;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public class GoodsDAO implements GoodsDAOInterface, GoodsSchema {
    private static final String TAG = "GoodsDAO";
    private static final String ORDER_BY = "id DESC";
    private final Context context;

    public GoodsDAO(Context context) {
        this.context = context;
    }

    @Override
    public HashMap<Shop, Integer> add(List<Good> listOfItems) {
        List<Integer> oldListOfGoodsIDPlus = getAllID(Shop.PLUS);
        List<Integer> oldListOfGoodsIDDishes = getAllID(Shop.DISHES);
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context).query(TAG_TABLE_GOODS,
                    null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int clearCount = DatabaseProvider.getInstance(context).delete(TAG_TABLE_GOODS, null, null);
                Log.i(TAG, "add:  SUCCESS DELETE, delete number of rows: "
                        + clearCount);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        for (int i = 0; i < listOfItems.size(); i++) {
            ContentValues contentValues = new ContentValues();
            Good good = listOfItems.get(i);

            contentValues.put(TAG_ID, good.getId());
            contentValues.put(TAG_SHOP, good.getShop());
            contentValues.put(TAG_TITLE, good.getTitle());
            contentValues.put(TAG_CATALOG, good.getCatalog());
            contentValues.put(TAG_IMAGE, good.getImage());
            contentValues.put(TAG_NEW_PRICE, good.getNew_price());
            contentValues.put(TAG_OLD_PRICE, good.getOld_price());
            contentValues.put(TAG_DESCRIPTION, good.getDescription());
            contentValues.put(TAG_CREATED_AT, good.getCreated_at());

            Log.i(TAG, "add:  Result SQL insert operation: "
                    + String.valueOf(DatabaseProvider.getInstance(context)
                    .insert(TAG_TABLE_GOODS, null, contentValues))
                    + ", Good size of which is transmitted database: "
                    + String.valueOf(contentValues.size()));
        }
        List<Integer> newListOfGoodsIDPlus = getAllID(Shop.PLUS);
        List<Integer> newListOfGoodsIDDishes = getAllID(Shop.DISHES);
        newListOfGoodsIDPlus.removeAll(oldListOfGoodsIDPlus);
        newListOfGoodsIDDishes.removeAll(oldListOfGoodsIDDishes);



        HashMap<Shop, Integer> newContent = new HashMap<>();
        newContent.put(Shop.PLUS, newListOfGoodsIDPlus.size());
        newContent.put(Shop.DISHES, newListOfGoodsIDDishes.size());
        return newContent;
    }

    @Override
    public List<Good> getAll(Shop shop) {
        String whereArgs = "shop = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase()};
        return something(whereArgs, whereValues);
    }

    @Override
    public List<Good> getAll(Shop shop, String catalog) {
        Log.d(TAG, "getAll: shop - " + shop + ", catalog - " + catalog);
        String whereArgs = "shop = ? AND catalog = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase(), catalog};
        return something(whereArgs, whereValues);
    }

    private List<Good> something(String whereArgs, String[] whereValues) {
        List<Good> listOfItems = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context)
                    .query(TAG_TABLE_GOODS, null, whereArgs, whereValues, null, null, ORDER_BY);
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
                    Good object = new Good();

                    object.setId(cursor.getInt(idColIndex));
                    object.setShop(cursor.getString(shopColIndex));
                    object.setTitle(cursor.getString(titleColIndex));
                    object.setCatalog(cursor.getString(catalogColIndex));
                    object.setDescription(cursor.getString(descriptionColIndex));
                    object.setImage(cursor.getString(imageColIndex));
                    object.setNew_price(cursor.getString(new_priceColIndex));
                    object.setOld_price(cursor.getString(old_priceColIndex));
                    object.setCreated_at(cursor.getString(created_atColIndex));

                    listOfItems.add(object);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return listOfItems;
    }

    @Override
    public List<Integer> getAllID(Shop shop) {
        List<Integer> listOfID = new ArrayList<>();
        String whereArgs = "shop = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase()};
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context)
                    .query(TAG_TABLE_GOODS, null, whereArgs, whereValues, null, null, null);
            int idColIndex = cursor.getColumnIndex(TAG_ID);

            if (cursor.moveToFirst()) {
                do {
                    listOfID.add(cursor.getInt(idColIndex));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return listOfID;
    }
}
