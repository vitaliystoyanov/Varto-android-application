package es.esy.varto_novomyrgorod.varto.utility;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtility {
    private static final String TAG = "JsonUtility";

    public static JSONObject toJSONObject(String jsonRaw) {
        JSONObject jsonObject = null;
        try {
            if (jsonRaw != null) jsonObject = new JSONObject(jsonRaw);
        } catch (JSONException e) {
            Log.e(TAG, "getJSONObject: Cannot parse String", e);
        }
        return jsonObject;
    }
}
