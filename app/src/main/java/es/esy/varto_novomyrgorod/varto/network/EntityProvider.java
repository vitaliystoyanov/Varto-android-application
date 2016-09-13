package es.esy.varto_novomyrgorod.varto.network;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.network.parsers.CatalogParser;
import es.esy.varto_novomyrgorod.varto.network.parsers.GoodsParser;
import es.esy.varto_novomyrgorod.varto.network.parsers.NewsParser;
import es.esy.varto_novomyrgorod.varto.network.parsers.ScheduleParser;
import es.esy.varto_novomyrgorod.varto.pojo.Catalog;
import es.esy.varto_novomyrgorod.varto.pojo.Good;
import es.esy.varto_novomyrgorod.varto.pojo.News;
import es.esy.varto_novomyrgorod.varto.pojo.Schedule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EntityProvider implements APIUrl {
    private static final String TAG = "EntityProvider";
    private OkHttpClient client;

    public EntityProvider() {
        client = new OkHttpClient();
    }

    private JSONObject getJSONObject(String json) {
        JSONObject jsonObject = null;
        try {
            if (json != null) jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e(TAG, "getJSONObject: Cannot parse String", e);
        }
        return jsonObject;
    }

    public List<News> getNews() {
        JSONObject json = getJSONObject(request(URL_NEWS));
        return new NewsParser().parse(json);
    }

    public List<Catalog> getCatalogs() {
        JSONObject json = getJSONObject(request(URL_CATALOGS));
        return new CatalogParser().parse(json);
    }

    public List<Good> getGoods() {
        JSONObject json = getJSONObject(request(URL_GOODS));
        return new GoodsParser().parse(json);
    }

    public List<Schedule> getSchedules() {
        JSONObject json = getJSONObject(request(URL_SCHEDULE));
        return new ScheduleParser().parse(json);
    }

    @Nullable
    private String request(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "request: ", e);
        }
        return null;
    }
}