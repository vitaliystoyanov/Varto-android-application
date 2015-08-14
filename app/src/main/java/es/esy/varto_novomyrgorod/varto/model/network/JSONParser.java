package es.esy.varto_novomyrgorod.varto.model.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.NewsObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.SaleObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.ScheduleObject;

public class JSONParser extends JSONConstants {
    private HTTPRequestMaker request;

    public JSONParser() {
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

    public List<NewsObject> getNews() {
        String respond = request.makeGETRequest(ConfigurationURL.URL_NEWS_GET, null);
        JSONObject json = getJSONObject(respond);

        ArrayList<NewsObject> listOfParsedNews = null;
        if (json != null) {
            Log.d("DBG", "JSON URL_NEWS_GET: " + json.toString());
            listOfParsedNews = new ArrayList<>();
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
                        object.setImage(jsonObject.getString(JSON_IMAGE));
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

    public List<CatalogObject> getCatalogs() {
        String respond = request.makeGETRequest(ConfigurationURL.URL_CATALOGS, null);
        JSONObject json = getJSONObject(respond);

        ArrayList<CatalogObject> listCatalogs = null;
        if (json != null) {
            Log.d("DBG", "JSON from URL_CATALOGS: " + json.toString());
            try {
                int success = json.getInt(JSON_SUCCESS);
                if (success == 1) {
                    listCatalogs = new ArrayList<>();
                    JSONArray catalogsArray = json.getJSONArray(JSON_CATALOGS);
                    for (int i = 0; i < catalogsArray.length(); i++) {
                        JSONObject jsonObject = catalogsArray.getJSONObject(i);
                        listCatalogs.add(new CatalogObject(jsonObject.getString(JSON_SHOP),
                                jsonObject.getString(JSON_CATALOG)));
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

    public List<SaleObject> getSales() {
        String respond = request.makeGETRequest(ConfigurationURL.URL_SHARES, null);
        JSONObject json = getJSONObject(respond);

        ArrayList<SaleObject> listOfParsedShares = null;
        if (json != null) {
            Log.d("DBG", "JSON from URL_SALES: " + json.toString());
            try {
                int success = json.getInt(JSON_SUCCESS);
                if (success == 1) {
                    listOfParsedShares = new ArrayList<>();
                    JSONArray sharesArray = json.getJSONArray(JSON_SHARES);
                    for (int i = 0; i < sharesArray.length(); i++) {
                        JSONObject jsonObject = sharesArray.getJSONObject(i);
                        SaleObject object = new SaleObject();

                        object.setId(jsonObject.getInt(JSON_ID));
                        object.setShop(jsonObject.getString(JSON_SHOP));
                        object.setTitle(jsonObject.getString(JSON_TITLE));
                        object.setCatalog(jsonObject.getString(JSON_CATALOG));
                        object.setImage(jsonObject.getString(JSON_IMAGE));
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

    public List<ScheduleObject> getTimetables() {
        String respond = request.makeGETRequest(ConfigurationURL.URL_TIMETABLE, null);
        JSONObject json = getJSONObject(respond);

        ArrayList<ScheduleObject> listOfParsedTimetables = null;
        if (json != null) {
            Log.d("DBG", "JSON from URL_TIMETABLE: " + json.toString());
            try {
                int success = json.getInt(JSON_SUCCESS);
                if (success == 1) {
                    listOfParsedTimetables = new ArrayList<>();
                    JSONArray timetablesArray = json.getJSONArray(JSON_TIMETABLES);

                    for (int i = 0; i < timetablesArray.length(); i++) {
                        ScheduleObject object = new ScheduleObject();
                        JSONObject jsonObject = timetablesArray.getJSONObject(i);

                        object.setShop(jsonObject.getString(JSON_SHOP));
                        object.setSunday(jsonObject.getString(JSON_SUNDAY));
                        object.setMonday(jsonObject.getString(JSON_MONDAY));
                        object.setTuesday(jsonObject.getString(JSON_TUESDAY));
                        object.setWednesday(jsonObject.getString(JSON_WEDNESDAY));
                        object.setThursday(jsonObject.getString(JSON_THURSDAY));
                        object.setFriday(jsonObject.getString(JSON_FRIDAY));
                        object.setSaturday(jsonObject.getString(JSON_SATURDAY));

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
}