package it.polito.ezshop.model;

import java.time.LocalDate;
import it.polito.ezshop.data.*;


public class BalanceOperationImpl implements BalanceOperation {

    private String type; // credit or debit
    private double amount; // amount of the operation
    private LocalDate date; // date of the operation
    private Integer balanceId;

    public BalanceOperationImpl(String type, double amount, LocalDate date, Integer balanceId) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.balanceId = balanceId;
    }

    @Override
    public int getBalanceId() { return this.balanceId; }

    @Override
    public void setBalanceId(int balanceId) { this.balanceId = balanceId; }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public double getMoney() {
        return this.amount;
    }

    @Override
    public void setMoney(double money) {
        this.amount = money;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

}

