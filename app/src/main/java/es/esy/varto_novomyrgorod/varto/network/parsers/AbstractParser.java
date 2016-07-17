package es.esy.varto_novomyrgorod.varto.network.parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.network.JSONFields;

abstract class AbstractParser<T> implements JSONFields {

    private static final String TAG = "AbstractParser";

    public List<T> parse(JSONObject rawJSON) {
        if (rawJSON != null) {
            Log.d(TAG, "parse: Raw JSON - " + rawJSON.toString());
            try {
                if (rawJSON.getInt(JSON_SUCCESS) == 1) {
                    return parseToList(rawJSON);
                }
            } catch (JSONException e) {
                Log.e(TAG, "parse: Cannot parse JSON", e);
            } catch (Exception e) {
                Log.e(TAG, "parse: ", e);
            }
        }
        return Collections.emptyList();
    }

    protected abstract List<T> parseToList(JSONObject object) throws JSONException;
}
