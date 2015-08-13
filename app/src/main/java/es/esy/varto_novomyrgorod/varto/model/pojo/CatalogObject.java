package es.esy.varto_novomyrgorod.varto.model.pojo;

public class CatalogObject {
    private String name;
    private String shop;

    public CatalogObject(String pShop, String pName) {
        this.shop = pShop;
        this.name = pName;
    }

    public CatalogObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}
