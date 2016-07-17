package es.esy.varto_novomyrgorod.varto.pojo;

public class Catalog {
    private String name;
    private String shop;

    public Catalog(String shop, String name) {
        this.shop = shop;
        this.name = name;
    }

    public Catalog() {
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
