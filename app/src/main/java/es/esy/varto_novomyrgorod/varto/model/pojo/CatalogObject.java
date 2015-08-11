package es.esy.varto_novomyrgorod.varto.model.pojo;

public class CatalogObject {
    private String name;
    private String shop;

    public CatalogObject(String pShop, String pName) {
        shop = pShop;
        name = pName;
    }

    public CatalogObject() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
