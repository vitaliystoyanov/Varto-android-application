package es.esy.varto_novomyrgorod.varto.model.pojo;

public class InformationObject {
    private Integer amountOfNewsPlus;
    private Integer amountOfSharesPlus;
    private Integer amountOfNewsDishes;


    public void putAmountOfNewsDishes(Integer amount) {
        this.amountOfNewsDishes = amount;
    }

    public void putAmountOfNewsPlus(Integer amount) {
        this.amountOfNewsPlus = amount;
    }

    public Integer getAmountOfNewsPlus() {
        return amountOfNewsPlus;
    }

    public Integer getAmountOfNewsDishes() {
        return amountOfNewsDishes;
    }
}
