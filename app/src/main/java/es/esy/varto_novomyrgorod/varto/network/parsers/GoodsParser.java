package es.esy.varto_novomyrgorod.varto.network.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.pojo.Good;

public class GoodsParser extends AbstractParser<Good> {

    @Override
    protected List<Good> parseToList(JSONObject object) throws JSONException {
        List<Good> parsedGoods = new ArrayList<>();
        JSONArray sharesArray = object.getJSONArray(JSON_SHARES);
        for (int i = 0; i < sharesArray.length(); i++) {
            JSONObject jsonObject = sharesArray.getJSONObject(i);
            Good good = new Good();

            good.setId(jsonObject.getInt(JSON_ID));
            good.setShop(jsonObject.getString(JSON_SHOP));
            good.setTitle(jsonObject.getString(JSON_TITLE));
            good.setCatalog(jsonObject.getString(JSON_CATALOG));
            good.setImage(jsonObject.getString(JSON_IMAGE));
            good.setDescription(jsonObject.getString(JSON_DESCRIPTION));
            good.setNew_price(jsonObject.getString(JSON_NEW_PRICE));
            good.setOld_price(jsonObject.getString(JSON_OLD_PRICE));
            good.setCreated_at(jsonObject.getString(JSON_CREATED_AT));

            parsedGoods.add(good);
        }
        return parsedGoods;
    }
}
