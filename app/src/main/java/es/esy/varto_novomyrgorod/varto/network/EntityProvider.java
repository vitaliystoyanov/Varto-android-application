package es.esy.varto_novomyrgorod.varto.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.network.parsers.CatalogParser;
import es.esy.varto_novomyrgorod.varto.network.parsers.GoodsParser;
import es.esy.varto_novomyrgorod.varto.network.parsers.NewsParser;
import es.esy.varto_novomyrgorod.varto.network.parsers.ScheduleParser;
import es.esy.varto_novomyrgorod.varto.pojo.Catalog;
import es.esy.varto_novomyrgorod.varto.pojo.Good;
import es.esy.varto_novomyrgorod.varto.pojo.News;
import es.esy.varto_novomyrgorod.varto.pojo.Schedule;
import es.esy.varto_novomyrgorod.varto.utills.HTTPRequestMaker;

public class EntityProvider implements APIUrl {
    private static final String TAG = "EntityProvider";

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
        JSONObject json = getJSONObject(HTTPRequestMaker.sendGet(URL_NEWS));
        return new NewsParser().parse(json);
    }

    public List<Catalog> getCatalogs() {
        JSONObject json = getJSONObject(HTTPRequestMaker.sendGet(URL_CATALOGS));
        return new CatalogParser().parse(json);
    }

    public List<Good> getGoods() {
        JSONObject json = getJSONObject(HTTPRequestMaker.sendGet(URL_GOODS));
        return new GoodsParser().parse(json);
    }

    public List<Schedule> getSchedules() {
        JSONObject json = getJSONObject(HTTPRequestMaker.sendGet(URL_SCHEDULE));
        return new ScheduleParser().parse(json);
    }
}