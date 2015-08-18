package es.esy.varto_novomyrgorod.varto.model.pojo;

public class NewsObject {
    private Integer id;
    private String shop;
    private String image;
    private String title;
    private String article;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String timeAndDate) {
        this.created_at = timeAndDate;
    }
}
