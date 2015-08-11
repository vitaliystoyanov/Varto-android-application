package es.esy.varto_novomyrgorod.varto.model;

import android.content.Context;
import android.util.Log;

import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.NewsObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.SharesObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.ScheduleObject;

public class MemoryPreferences extends ComplexPreferences{
    private ComplexObject complexObject;

    //TODO наследование!
    public MemoryPreferences(Context context) {
        super(context, null, Context.MODE_PRIVATE);
        this.complexObject = getObject("object", ComplexObject.class);
        if (this.complexObject == null) Log.i("DBG", "complexObject == null");
    }

    public MemoryPreferences(Context context, ComplexObject object) {
        super(context, null, Context.MODE_PRIVATE);
        this.complexObject = object;
        putObject("object", object);
        commit();
    }

    public List<NewsObject> getNewsFromMemory() {
        return complexObject.getNewsObjects();
    }

    public List<SharesObject> getSharesFromMemory() {
        return complexObject.getSharesObjects();
    }

    public List<CatalogObject> getCatalogFromMemory() {
        return complexObject.getCatalogObjects();
    }

    public List<ScheduleObject> getTimetables() {
        return complexObject.getScheduleObjects();
    }
}
