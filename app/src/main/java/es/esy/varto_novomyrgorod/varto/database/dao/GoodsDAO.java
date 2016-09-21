package es.esy.varto_novomyrgorod.varto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.database.Database;
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
    public HashMap<Shop, Integer> update(List<Good> goods) {
        List<Integer> oldListOfGoodsIDPlus = getAllID(Shop.PLUS);
        List<Integer> oldListOfGoodsIDDishes = getAllID(Shop.DISHES);
        Cursor cursor = null;
        try {
            cursor = Database.getInstance(context).query(TAG_TABLE_GOODS,
                    null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int rowsAffected = Database.getInstance(context).delete(TAG_TABLE_GOODS, null, null);
                Log.i(TAG, "update:  SUCCESS DELETE, delete number of rows: "
                        + rowsAffected);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        for (int i = 0; i < goods.size(); i++) {
            ContentValues contentValues = new ContentValues();
            Good good = goods.get(i);
            contentValues.put(TAG_ID, good.getId());
            contentValues.put(TAG_SHOP, good.getShop());
            contentValues.put(TAG_TITLE, good.getTitle());
            contentValues.put(TAG_CATALOG, good.getCatalog());
            contentValues.put(TAG_IMAGE, good.getImage());
            contentValues.put(TAG_NEW_PRICE, good.getNewPrice());
            contentValues.put(TAG_OLD_PRICE, good.getOldPrice());
            contentValues.put(TAG_DESCRIPTION, good.getDescription());
            contentValues.put(TAG_CREATED_AT, good.getCreatedAt());

            Log.i(TAG, "update:  Result SQL insert operation: "
                    + String.valueOf(Database.getInstance(context)
                    .insert(TAG_TABLE_GOODS, null, contentValues))
                    + ", Good size of which is transmitted database: "
                    + String.valueOf(contentValues.size()));
        }
        List<Integer> newListOfGoodsIDPlus = getAllID(Shop.PLUS);
        List<Integer> newListOfGoodsIDDishes = getAllID(Shop.DISHES);
        newListOfGoodsIDPlus.removeAll(oldListOfGoodsIDPlus);
        newListOfGoodsIDDishes.removeAll(oldListOfGoodsIDDishes);

        HashMap<Shop, Integer> quantityOfNewGoods = new HashMap<>();
        quantityOfNewGoods.put(Shop.PLUS, newListOfGoodsIDPlus.size());
        quantityOfNewGoods.put(Shop.DISHES, newListOfGoodsIDDishes.size());
        return quantityOfNewGoods;
    }

    @Override
    public List<Good> getAll(Shop shop) {
        String whereArgs = "shop = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase()};
        return getAllWithParams(whereArgs, whereValues);
    }

    @Override
    public List<Good> getAll(Shop shop, String catalog) {
        Log.d(TAG, "getAll: shop - " + shop + ", catalog - " + catalog);
        String whereArgs = "shop = ? AND catalog = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase(), catalog};
        return getAllWithParams(whereArgs, whereValues);
    }

    private List<Good> getAllWithParams(String whereArgs, String[] whereValues) {
        List<Good> goods = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = Database.getInstance(context)
                    .query(TAG_TABLE_GOODS, null, whereArgs, whereValues, null, null, ORDER_BY);
            int idColIndex = cursor.getColumnIndex(TAG_ID);
            int shopColIndex = cursor.getColumnIndex(TAG_SHOP);
            int titleColIndex = cursor.getColumnIndex(TAG_TITLE);
            int catalogColIndex = cursor.getColumnIndex(TAG_CATALOG);
            int descriptionColIndex = cursor.getColumnIndex(TAG_DESCRIPTION);
            int imageColIndex = cursor.getColumnIndex(TAG_IMAGE);
            int newPriceColIndex = cursor.getColumnIndex(TAG_NEW_PRICE);
            int oldPriceColIndex = cursor.getColumnIndex(TAG_OLD_PRICE);
            int createdAtColIndex = cursor.getColumnIndex(TAG_CREATED_AT);

            if (cursor.moveToFirst()) {
                do {
                    Good item = new Good();
                    item.setId(cursor.getInt(idColIndex));
                    item.setShop(cursor.getString(shopColIndex));
                    item.setTitle(cursor.getString(titleColIndex));
                    item.setCatalog(cursor.getString(catalogColIndex));
                    item.setDescription(cursor.getString(descriptionColIndex));
                    item.setImage(cursor.getString(imageColIndex));
                    item.setNewPrice(cursor.getString(newPriceColIndex));
                    item.setOldPrice(cursor.getString(oldPriceColIndex));
                    item.setCreatedAt(cursor.getString(createdAtColIndex));

                    goods.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return goods;
    }

    @Override
    public List<Integer> getAllID(Shop shop) {
        List<Integer> Ids = new ArrayList<>();
        String whereArgs = "shop = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase()};
        Cursor cursor = null;
        try {
            cursor = Database.getInstance(context)
                    .query(TAG_TABLE_GOODS, null, whereArgs, whereValues, null, null, null);
            int idColIndex = cursor.getColumnIndex(TAG_ID);

            if (cursor.moveToFirst()) {
                do {
                    Ids.add(cursor.getInt(idColIndex));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return Ids;
    }
}
