package es.esy.varto_novomyrgorod.varto.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.HashMap;

import es.esy.varto_novomyrgorod.varto.database.dao.CatalogsDAO;
import es.esy.varto_novomyrgorod.varto.database.dao.GoodsDAO;
import es.esy.varto_novomyrgorod.varto.database.dao.NewsDAO;
import es.esy.varto_novomyrgorod.varto.database.dao.ScheduleDAO;
import es.esy.varto_novomyrgorod.varto.network.EntityProvider;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public class ContentIntentService extends IntentService {
    private static final String TAG = "ContentIntentService";
    private static final String ACTION_CONTENT = "es.esy.varto_novomyrgorod.varto.action.sync.content";
    public static final String BROADCAST_ACTION = "es.esy.varto_novomyrgorod.varto.action.broadcast";
    public static final String EXTRA_NEWS_PLUS = "news_plus";
    public static final String EXTRA_NEWS_DISHES = "news_dishes";
    public static final String EXTRA_GOODS_PLUS = "goods_plus";
    public static final String EXTRA_GOODS_DISHES = "goods_dishes";

    public ContentIntentService() {
        super("ContentIntentService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ContentIntentService.class);
        intent.setAction(ACTION_CONTENT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CONTENT.equals(action)) {
                EntityProvider entityProvider = new EntityProvider();
                Intent localIntent = new Intent(BROADCAST_ACTION);

                HashMap<Shop, Integer> newContent = new NewsDAO(getApplicationContext()).add(entityProvider.getNews());
                localIntent.putExtra(EXTRA_NEWS_PLUS, newContent.get(Shop.PLUS));
                localIntent.putExtra(EXTRA_NEWS_DISHES, newContent.get(Shop.DISHES));
                Log.d(TAG, "onHandleIntent: fresh news plus - " + newContent.get(Shop.PLUS)
                        + ", fresh news dishes - " + newContent.get(Shop.DISHES));

                newContent = new GoodsDAO(getApplicationContext()).add(entityProvider.getGoods());
                localIntent.putExtra(EXTRA_GOODS_PLUS, newContent.get(Shop.PLUS));
                localIntent.putExtra(EXTRA_GOODS_DISHES, newContent.get(Shop.DISHES));
                Log.d(TAG, "onHandleIntent: fresh goods plus - " + newContent.get(Shop.PLUS)
                        + ", fresh goods dishes - " + newContent.get(Shop.DISHES));

                new CatalogsDAO(getApplicationContext()).add(entityProvider.getCatalogs());
                new ScheduleDAO(getApplicationContext()).add(entityProvider.getSchedules());

                LocalBroadcastManager
                        .getInstance(getApplicationContext())
                        .sendBroadcast(localIntent);
            }
        } else {
            throw new IllegalArgumentException("intent null");
        }
    }
}
