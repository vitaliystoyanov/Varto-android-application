package es.esy.varto_novomyrgorod.varto.pojo;

public class Good {
    private Integer id;
    private String shop;
    private String catalog;
    private String title;
    private String image;
    private String description;
    private String new_price;
    private String old_price;
    private String created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewPrice() {
        return new_price;
    }

    public void setNewPrice(String new_price) {
        this.new_price = new_price;
    }

    public String getOldPrice() {
        return old_price;
    }

    public void setOldPrice(String old_price) {
        this.old_price = old_price;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }
}
