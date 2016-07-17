package es.esy.varto_novomyrgorod.varto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import es.esy.varto_novomyrgorod.varto.database.DatabaseProvider;
import es.esy.varto_novomyrgorod.varto.database.dao.interfaces.QuantityDAOInterface;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.QuantitySchema;
import es.esy.varto_novomyrgorod.varto.pojo.Quantity;

public class QuantityDAO implements QuantityDAOInterface, QuantitySchema {
    private static final String TAG = "QuantityDAO";
    private final Context context;

    public QuantityDAO(Context context) {
        this.context = context;
    }

    @Override
    public void add(Quantity quantity) {
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context)
                    .query(TAG_TABLE_QUANTITY, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int clearCount = DatabaseProvider.getInstance(context)
                        .delete(TAG_TABLE_QUANTITY, null, null);
                Log.i(TAG, "[TABLE: inforamation]SQL:  SUCCESS DELETE, delete number of rows: " + clearCount);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_QUANTITY_NEWS_PLUS, quantity.getAmountOfNewsPlus());
        contentValues.put(TAG_QUANTITY_NEWS_DISHES, quantity.getAmountOfNewsDishes());
        contentValues.put(TAG_QUANTITY_GOODS_PLUS, quantity.getAmountOfGoodsPlus());
        contentValues.put(TAG_QUANTITY_GOODS_DISHES, quantity.getAmountOfGoodsDishes());

        Log.i(TAG, "news plus = " + quantity.getAmountOfNewsPlus() +
                ", news dishes = " + quantity.getAmountOfNewsDishes() +
                ", goods plus = " + quantity.getAmountOfGoodsPlus() +
                ", goods dishes = " + quantity.getAmountOfGoodsDishes());
        Log.i(TAG, "[TABLE: inforamation]SQL:  Result SQL insert operation: "
                + String.valueOf(DatabaseProvider.getInstance(context)
                .insert(TAG_TABLE_QUANTITY, null, contentValues)));
    }

    @Override
    public Quantity get() {
        Quantity quantity = new Quantity();
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context)
                    .query(TAG_TABLE_QUANTITY, null, null, null, null, null, null);
            int newsPlusColIndex = cursor.getColumnIndex(TAG_QUANTITY_NEWS_PLUS);
            int newsDishesColIndex = cursor.getColumnIndex(TAG_QUANTITY_NEWS_DISHES);
            int goodsPlusColIndex = cursor.getColumnIndex(TAG_QUANTITY_GOODS_PLUS);
            int goodsDishesColIndex = cursor.getColumnIndex(TAG_QUANTITY_GOODS_DISHES);
            if (cursor.moveToFirst()) {
                do {
                    quantity.setAmountOfNewsPlus(cursor.getInt(newsPlusColIndex));
                    quantity.setAmountOfNewsDishes(cursor.getInt(newsDishesColIndex));
                    quantity.setAmountOfGoodsPlus(cursor.getInt(goodsPlusColIndex));
                    quantity.setAmountOfGoodsDishes(cursor.getInt(goodsDishesColIndex));
                } while (cursor.moveToNext());
                Log.i(TAG, "news plus = " + quantity.getAmountOfNewsPlus() + ", " +
                        "news dishes = " + quantity.getAmountOfNewsDishes() + ", " +
                        "goods plus = " + quantity.getAmountOfGoodsPlus() + ", " +
                        "goods dishes = " + quantity.getAmountOfGoodsDishes());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return quantity;
    }
}
