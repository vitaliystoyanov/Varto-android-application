package es.esy.varto_novomyrgorod.varto.network.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.Catalog;

public class CatalogParser extends AbstractParser<Catalog> {

    @Override
    protected List<Catalog> parseToList(JSONObject object) throws JSONException {
        ArrayList<Catalog> parsedCatalogs = new ArrayList<>();
        JSONArray catalogsArray = object.getJSONArray(JSON_CATALOGS);
        for (int i = 0; i < catalogsArray.length(); i++) {
            JSONObject jsonObject = catalogsArray.getJSONObject(i);
            parsedCatalogs.add(new Catalog(jsonObject.getString(JSON_SHOP),
                    jsonObject.getString(JSON_CATALOG)));

        }
        return parsedCatalogs;
    }
}
