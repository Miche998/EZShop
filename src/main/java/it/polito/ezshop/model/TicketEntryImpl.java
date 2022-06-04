package it.polito.ezshop.model;
import it.polito.ezshop.data.*;

public class TicketEntryImpl implements TicketEntry {

    private String productBarcode;
    private String productDescription;
    private int amount;
    private double pricePerUnit;
    private double discountRate;

    public TicketEntryImpl(String productBarcode, String productDescription, int amount, double pricePerUnit, double discountRate) {
        this.productBarcode = productBarcode;
        this.productDescription = productDescription;
        this.amount = amount;
        this.pricePerUnit = pricePerUnit;
        this.discountRate = discountRate;
    }

    @Override
    public String getBarCode() {
        return this.productBarcode;
    }

    @Override
    public void setBarCode(String barCode) { this.productBarcode = barCode; }

    @Override
    public String getProductDescription() {
        return this.productDescription;
    }

    @Override
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(int newAmount) {
        this.amount = newAmount;
    }

    @Override
    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    @Override
    public void setPricePerUnit(double newPricePerUnit) {
        this.pricePerUnit = newPricePerUnit;
    }

    @Override
    public double getDiscountRate() {
        return this.discountRate;
    }

    @Override
    public void setDiscountRate(double newDiscountRate) {
        this.discountRate = newDiscountRate;
    }
}
