package es.esy.varto_novomyrgorod.varto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.database.DatabaseProvider;
import es.esy.varto_novomyrgorod.varto.database.dao.interfaces.NewsDAOInterface;
import es.esy.varto_novomyrgorod.varto.database.dao.schema.NewsSchema;
import es.esy.varto_novomyrgorod.varto.pojo.News;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public class NewsDAO implements NewsDAOInterface, NewsSchema {

    private static final String TAG = "NewsDAO";
    private final Context context;

    public NewsDAO(Context context) {
        this.context = context;
    }

    @Override
    public HashMap<Shop, Integer> add(@NonNull List<News> listOfItems) {
        List<Integer> oldListOfNewsIDPlus = getAllNewsID(Shop.PLUS);
        List<Integer> oldListOfNewsIDDishes = getAllNewsID(Shop.DISHES);
        Log.i(TAG, "addNews: size - " + listOfItems.size());
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context).query(TAG_TABLE_NEWS,
                    null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int clearCount = DatabaseProvider.getInstance(context).delete(TAG_TABLE_NEWS,
                        null, null);
                Log.i(TAG, "[TABLE: news]SQL:  SUCCESS DELETE, delete number of rows: " + clearCount);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        for (int i = 0; i < listOfItems.size(); i++) {
            ContentValues contentValues = new ContentValues();
            News news = listOfItems.get(i);

            contentValues.put(TAG_ID, news.getId());
            contentValues.put(TAG_SHOP, news.getShop());
            contentValues.put(TAG_TITLE, news.getTitle());
            contentValues.put(TAG_IMAGE, news.getImage());
            contentValues.put(TAG_ARTICLE, news.getArticle());
            contentValues.put(TAG_CREATED_AT, news.getCreatedAt());

            Log.i(TAG, "addNews:  Result SQL insert operation: "
                    + String.valueOf(DatabaseProvider.getInstance(context).insert(TAG_TABLE_NEWS,
                    null, contentValues)) + ", News size of which is transmitted database: "
                    + String.valueOf(contentValues.size()));
        }
        List<Integer> newListOfNewsIDPlus = getAllNewsID(Shop.PLUS);
        List<Integer> newListOfNewsIDDishes = getAllNewsID(Shop.DISHES);
        newListOfNewsIDPlus.removeAll(oldListOfNewsIDPlus);
        newListOfNewsIDDishes.removeAll(oldListOfNewsIDDishes);

        HashMap<Shop, Integer> newContent = new HashMap<>();
        newContent.put(Shop.PLUS, newListOfNewsIDPlus.size());
        newContent.put(Shop.DISHES, newListOfNewsIDDishes.size());
        return newContent;
    }

    @NonNull
    @Override
    public List<News> getAll(Shop shop) {
        ArrayList<News> news = new ArrayList<>();
        String whereArgs = "shop = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase()};
        Cursor cursor = null;
        try {
            String orderBy = "id DESC";
            cursor = DatabaseProvider.getInstance(context).query(TAG_TABLE_NEWS, null,
                    whereArgs, whereValues, null, null, orderBy);
            int idColIndex = cursor.getColumnIndex(TAG_ID);
            int shopColIndex = cursor.getColumnIndex(TAG_SHOP);
            int titleColIndex = cursor.getColumnIndex(TAG_TITLE);
            int articleColIndex = cursor.getColumnIndex(TAG_ARTICLE);
            int imageColIndex = cursor.getColumnIndex(TAG_IMAGE);
            int created_atColIndex = cursor.getColumnIndex(TAG_CREATED_AT);

            if (cursor.moveToFirst()) {
                do {
                    News object = new News();

                    object.setId(cursor.getInt(idColIndex));
                    object.setShop(cursor.getString(shopColIndex));
                    object.setTitle(cursor.getString(titleColIndex));
                    object.setArticle(cursor.getString(articleColIndex));
                    object.setImage(cursor.getString(imageColIndex));
                    object.setCreatedAt(cursor.getString(created_atColIndex));

                    news.add(object);
                } while (cursor.moveToNext());
                Log.i(TAG, "[TABLE: news]SQL:  Total objects in the ArrayList<News>"
                        + ",which will return method: "
                        + news.size());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return news;
    }

    public void getCountNewContent(Shop shop) {

    }

    @NonNull
    @Override
    public List<Integer> getAllNewsID(Shop shop) {
        ArrayList<Integer> news = new ArrayList<>();
        String whereArgs = "shop = ?";
        String[] whereValues = new String[]{shop.toString().toLowerCase()};
        Cursor cursor = null;
        try {
            cursor = DatabaseProvider.getInstance(context).query(TAG_TABLE_NEWS, null,
                    whereArgs, whereValues, null, null, null);
            int idColIndex = cursor.getColumnIndex(TAG_ID);

            if (cursor.moveToFirst()) {
                do {
                    news.add(cursor.getInt(idColIndex));
                } while (cursor.moveToNext());
                Log.i(TAG, "getAllNewsID: Total objects in DB"
                        + ", which it will return a method: " + news.size());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return news;
    }
}
