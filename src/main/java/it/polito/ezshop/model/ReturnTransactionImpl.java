package it.polito.ezshop.model;

import it.polito.ezshop.data.TicketEntry;

import java.util.ArrayList;
import java.util.List;

public class ReturnTransactionImpl {

    private Integer returnId;
    private Integer transactionId;
    private double price;
    private String status;
    private List<TicketEntry> ticketEntries = new ArrayList<>();
    private List<ProductImpl> RFIDs = new ArrayList<>();

    public ReturnTransactionImpl(Integer returnId, Integer transactionId, double price, String status) {
        this.returnId = returnId;
        this.transactionId = transactionId;
        this.price = price;
        this.status = status;
    }

    public Integer getReturnId() { return returnId; }

    public void setReturnId(Integer returnId) { this.returnId = returnId; }

    public Integer getTransactionId() { return transactionId; }

    public void setTransactionId(Integer transactionId) { this.transactionId = transactionId; }

    public double getPrice() { return this.price; }

    public void setPrice(double newPrice) { this.price = newPrice; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public List<TicketEntry> getEntries() {
        return this.ticketEntries ;
    }

    public void setEntries(List<TicketEntry> entries) { this.ticketEntries = entries; }

    public void computePrice(){

        this.price = 0;

        for(TicketEntry t : this.ticketEntries){
            this.price = this.price + (t.getPricePerUnit()*t.getAmount()*(1-t.getDiscountRate()));
        }
    }

    public List<ProductImpl> getRFIDs() {
        return RFIDs;
    }
}
