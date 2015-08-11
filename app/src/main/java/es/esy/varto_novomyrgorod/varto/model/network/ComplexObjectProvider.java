package es.esy.varto_novomyrgorod.varto.model.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.ComplexObject;
import es.esy.varto_novomyrgorod.varto.model.network.ConfigurationURL;
import es.esy.varto_novomyrgorod.varto.model.network.HTTPRequestMaker;
import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.NewsObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.SharesObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.ScheduleObject;

public class ComplexObjectProvider {
    public static final String JSON_SUCCESS = "success";
    public static final String JSON_NEWS = "news";
    public static final String JSON_CATALOGS = "catalogs";
    public static final String JSON_SHARES = "shares";
    public static final String JSON_ID = "id";
    public static final String JSON_SHOP = "shop";
    public static final String JSON_TITLE = "title";
    public static final String JSON_ARTICLE = "article";
    public static final String JSON_CREATED_AT = "created_at";
    public static final String JSON_DESCRIPTION = "description";
    public static final String JSON_OLD_PRICE = "old_price";
    public static final String JSON_NEW_PRICE = "new_price";
    public static final String JSON_TIMETABLES = "timetables";
    private HTTPRequestMaker request;

    public ComplexObjectProvider() {
        request = new HTTPRequestMaker();
    }

    private JSONObject getJSONObject(String json) {
        JSONObject jObj = null;
        try {
            if (json != null) jObj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    //TODO рефакторинг и переделка.
    private List<NewsObject> getNews() {
        String respond = request.makeGETRequest(ConfigurationURL.URL_NEWS_GET, null);
        JSONObject json = getJSONObject(respond);

        ArrayList<NewsObject> listOfParsedNews = null;
        if (json != null) {
            Log.d("DBG", "JSON URL_NEWS_GET: " + json.toString());
            listOfParsedNews = new ArrayList<NewsObject>();
            try {
                int success = json.getInt(JSON_SUCCESS);
                if (success == 1) {
                    JSONArray newsArray = json.getJSONArray(JSON_NEWS);
                    for (int i = 0; i < newsArray.length(); i++) {
                        JSONObject jsonObject = newsArray.getJSONObject(i);
                        NewsObject object = new NewsObject();

                        object.setId(jsonObject.getInt(JSON_ID));
                        object.setShop(jsonObject.getString(JSON_SHOP));
                        object.setTitle(jsonObject.getString(JSON_TITLE));
                        object.setArticle(jsonObject.getString(JSON_ARTICLE));
                        object.setCreated_at(jsonObject.getString(JSON_CREATED_AT));

                        listOfParsedNews.add(object);
                    }

                    return listOfParsedNews;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    private List<CatalogObject> getCatalog() {
        String respond = request.makeGETRequest(ConfigurationURL.URL_CATALOGS, null);
        JSONObject json = getJSONObject(respond);

        ArrayList<CatalogObject> listCatalogs = null;
        if (json != null) {
            Log.d("DBG", "JSON from URL_CATALOGS: " + json.toString());
            try {
                int success = json.getInt(JSON_SUCCESS);
                if (success == 1) {
                    listCatalogs = new ArrayList<CatalogObject>();
                    JSONArray catalogsArray = json.getJSONArray(JSON_CATALOGS);
                    for (int i = 0; i < catalogsArray.length(); i++) {
                        JSONObject jsonObject = catalogsArray.getJSONObject(i);
                        listCatalogs.add(new CatalogObject(jsonObject.getString(JSON_CATALOGS),
                                jsonObject.getString(JSON_SHOP)));
                    }

                    return listCatalogs;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    private List<SharesObject> getShares() {
        String respond = request.makeGETRequest(ConfigurationURL.URL_SHARES, null);
        JSONObject json = getJSONObject(respond);

        ArrayList<SharesObject> listOfParsedShares = null;
        if (json != null) {
            Log.d("DBG", "JSON from URL_SHARES: " + json.toString());
            try {
                int success = json.getInt(JSON_SUCCESS);
                if (success == 1) {
                    listOfParsedShares = new ArrayList<SharesObject>();
                    JSONArray sharesArray = json.getJSONArray(JSON_SHARES);
                    for (int i = 0; i < sharesArray.length(); i++) {
                        JSONObject jsonObject = sharesArray.getJSONObject(i);
                        SharesObject object = new SharesObject();

                        object.setId(jsonObject.getInt(JSON_ID));
                        object.setShop(jsonObject.getString(JSON_SHOP));
                        object.setTitle(jsonObject.getString(JSON_TITLE));
                        object.setDescription(jsonObject.getString(JSON_DESCRIPTION));
                        object.setNew_price(jsonObject.getString(JSON_NEW_PRICE));
                        object.setOld_price(jsonObject.getString(JSON_OLD_PRICE));
                        object.setCreated_at(jsonObject.getString(JSON_CREATED_AT));

                        listOfParsedShares.add(object);
                    }

                    return listOfParsedShares;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    private List<ScheduleObject> getTimetables() {
        String respond = request.makeGETRequest(ConfigurationURL.URL_TIMETABLE, null);
        JSONObject json = getJSONObject(respond);

        ArrayList<ScheduleObject> listOfParsedTimetables = null;
        if (json != null) {
            Log.d("DBG", "JSON from URL_TIMETABLE: " + json.toString());
            try {
                int success = json.getInt(JSON_SUCCESS);
                if (success == 1) {
                    listOfParsedTimetables = new ArrayList<ScheduleObject>();
                    JSONArray timetablesArray = json.getJSONArray(JSON_TIMETABLES);

                    for (int i = 0; i < timetablesArray.length(); i++) {
                        ScheduleObject object = new ScheduleObject();
                        JSONObject jsonObject = timetablesArray.getJSONObject(i);

                        object.setShop(jsonObject.getString(JSON_SHOP));
                        object.setSunday(jsonObject.getString("sunday"));
                        object.setMonday(jsonObject.getString("monday"));
                        object.setTuesday(jsonObject.getString("tuesday"));
                        object.setWednesday(jsonObject.getString("wednesday"));
                        object.setThursday(jsonObject.getString("thursday"));
                        object.setFriday(jsonObject.getString("friday"));
                        object.setSaturday(jsonObject.getString("saturday"));

                        listOfParsedTimetables.add(object);
                    }
                    return listOfParsedTimetables;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Collections.emptyList();
    }

    public ComplexObject getComplexObject() {
        ComplexObject object = null;

        List<NewsObject> news = getNews();
        //ArrayList<CatalogObject> catalogs = getCatalog();
        List<SharesObject> shares = getShares();
        List<ScheduleObject> timetables = getTimetables();

        object = new ComplexObject();
        object.setNewsObjects(news);
        //object.setCatalogObjects(catalogs);
        object.setSharesObjects(shares);
        object.setScheduleObjects(timetables);

        return object;
    }
}