package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.model.TicketEntryImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestUpdateSaleTransaction {
    DBManager db = new DBManager();

    @Test
    public void TestUpdateTransactionCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        s1.setDiscountRate(0.0);
        boolean res = db.updateSaleTransaction(s1);
        assertTrue(res);
        SaleTransactionImpl s = db.getSaleTransaction(1);
        assertEquals(s.getDiscountRate(), s1.getDiscountRate(), 0);
    }

    @Test
    public void TestUpdateTransactionEntriesCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        List<TicketEntry> tickets = new ArrayList<>();
        tickets.add(new TicketEntryImpl("123456789104","try",10,20.0,10.0));
        s1.setEntries(tickets);

        db.addClosedSaleTransaction(s1);
        s1.setDiscountRate(0.0);
        s1.getEntries().get(0).setAmount(20);

        boolean res = db.updateSaleTransaction(s1);
        assertTrue(res);

        SaleTransactionImpl s = db.getSaleTransaction(1);
        assertEquals(s.getDiscountRate(), s1.getDiscountRate(), 0);
        assertEquals(s.getEntries().get(0).getAmount(),20);
    }

    @Test
    public void TestUpdateTransactionNotExistent(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        s1.setTicketNumber(2);
        boolean res = db.updateSaleTransaction(s1);
        assertFalse(res);
    }

}
