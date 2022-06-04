package it.polito.ezshop.model;

public class LoyaltyCardImpl{
    private String cardCode;
    private Integer points;

    public LoyaltyCardImpl(String cardCode, Integer points) {
        this.cardCode = cardCode;
        this.points = points;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
