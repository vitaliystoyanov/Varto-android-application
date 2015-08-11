package es.esy.varto_novomyrgorod.varto.model;

import java.util.ArrayList;
import java.util.List;

import es.esy.varto_novomyrgorod.varto.model.pojo.CatalogObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.InformationObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.NewsObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.SharesObject;
import es.esy.varto_novomyrgorod.varto.model.pojo.ScheduleObject;

public class ComplexObject {
    private List<NewsObject> newsObjects;
    private List<CatalogObject> catalogObjects;
    private List<SharesObject> sharesObjects;
    private List<ScheduleObject> scheduleObjects;

    public ComplexObject() {
        newsObjects = new ArrayList<NewsObject>();
        sharesObjects = new ArrayList<SharesObject>();
        catalogObjects = new ArrayList<CatalogObject>();
        scheduleObjects = new ArrayList<ScheduleObject>();
    }

    public List<NewsObject> getNewsObjects() {
        return newsObjects;
    }

    public void setNewsObjects(List<NewsObject> newsObjects) {
        this.newsObjects = newsObjects;
    }

    public List<CatalogObject> getCatalogObjects() {
        return catalogObjects;
    }

    public void setCatalogObjects(List<CatalogObject> catalogObjects) {
        this.catalogObjects = catalogObjects;
    }

    public List<SharesObject> getSharesObjects() {
        return sharesObjects;
    }

    public void setSharesObjects(List<SharesObject> sharesObjects) {
        this.sharesObjects = sharesObjects;
    }

    public List<ScheduleObject> getScheduleObjects() {
        return scheduleObjects;
    }

    public void setScheduleObjects(List<ScheduleObject> scheduleObjects) {
        this.scheduleObjects = scheduleObjects;
    }
}