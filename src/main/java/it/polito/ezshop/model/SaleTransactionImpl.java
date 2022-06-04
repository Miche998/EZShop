package it.polito.ezshop.model;

import it.polito.ezshop.data.*;

import java.util.*;


public class SaleTransactionImpl implements SaleTransaction {

    private Integer ticketNumber;
    private double discount_rate;
    private double price;
    private String status;
    private List<TicketEntry> ticketEntries = new ArrayList<>();
    private List<ProductImpl> RFIDs = new ArrayList<>();


    public SaleTransactionImpl(Integer ticketNumber, double discount_rate, double price, String status) {
        this.ticketNumber = ticketNumber;
        this.discount_rate = discount_rate;
        this.price = price;
        this.status = status;
    }

    @Override
    public Integer getTicketNumber() {
        return this.ticketNumber;
    }

    @Override
    public void setTicketNumber(Integer newTicketNumber) {
        this.ticketNumber = newTicketNumber;
    }

    @Override
    public List<TicketEntry> getEntries() {
        return this.ticketEntries ;
    }

    @Override
    public void setEntries(List<TicketEntry> entries) { this.ticketEntries = entries; }

    @Override
    public double getDiscountRate() {
        return this.discount_rate;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discount_rate = discountRate;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void modifyEntryByBarcode(String barCode, int amount){

        for(TicketEntry t : this.ticketEntries){
            if(t.getBarCode().equals(barCode)){
                t.setAmount(t.getAmount()+amount);
            }
        }
    }

    public void computePrice(){

        this.price = 0;

        for(TicketEntry t : this.ticketEntries){
            this.price = this.price + (t.getPricePerUnit()*t.getAmount()*(1-t.getDiscountRate()));
        }

        this.price = this.price*(1-this.discount_rate);
    }

    public List<ProductImpl> getRFIDs() {
        return RFIDs;
    }

    public void setRFIDs(List<ProductImpl> newRFiDs) {
        this.RFIDs = newRFiDs;
    }
}
