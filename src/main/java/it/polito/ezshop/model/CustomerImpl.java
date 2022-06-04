package it.polito.ezshop.model;

import it.polito.ezshop.data.*;

public class CustomerImpl implements Customer {
    private String customerName;
    private Integer id;
    private LoyaltyCardImpl loyaltyCard;

    public CustomerImpl(String customerName, Integer id, LoyaltyCardImpl loyaltyCard) {
        this.customerName = customerName;
        this.id = id;
        this.loyaltyCard = loyaltyCard;
    }

    @Override
    public String getCustomerName() {
        return this.customerName;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String getCustomerCard() { return this.loyaltyCard.getCardCode(); }

    @Override
    public void setCustomerCard(String customerCard) { this.loyaltyCard.setCardCode(customerCard); }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getPoints() {
        return this.loyaltyCard.getPoints();
    }

    @Override
    public void setPoints(Integer points) {
        this.loyaltyCard.setPoints(points);
    }

    public LoyaltyCardImpl getLoyaltyCard(){
        return this.loyaltyCard;
    }

    public void setLoyaltyCard(LoyaltyCardImpl loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }
}
