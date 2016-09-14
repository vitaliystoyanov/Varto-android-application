package es.esy.varto_novomyrgorod.varto.network.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.News;

public class NewsParser extends AbstractParser<News> {

    @Override
    protected List<News> parseToList(JSONObject object) throws JSONException {
        ArrayList<News> parsedNews = new ArrayList<>();
        JSONArray newsArray = object.getJSONArray(JSON_NEWS);
        for (int i = 0; i < newsArray.length(); i++) {
            JSONObject jsonObject = newsArray.getJSONObject(i);
            News news = new News();

            news.setId(jsonObject.getInt(JSON_ID));
            news.setShop(jsonObject.getString(JSON_SHOP));
            news.setTitle(jsonObject.getString(JSON_TITLE));
            news.setImage(jsonObject.getString(JSON_IMAGE));
            news.setArticle(jsonObject.getString(JSON_ARTICLE));
            news.setCreatedAt(jsonObject.getString(JSON_CREATED_AT));
            parsedNews.add(news);
        }
        return parsedNews;
    }
}
