package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.model.TicketEntryImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestGetSaleTransaction {

    DBManager db = new DBManager();

    @Test
    public void TestGetTransactionCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        SaleTransactionImpl s2 = new SaleTransactionImpl(2,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedSaleTransaction(s2);

        SaleTransactionImpl res = db.getSaleTransaction(2);
        assertSame(res.getTicketNumber(), s2.getTicketNumber());
    }

    @Test
    public void TestGetTransactionNotExistent(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        SaleTransactionImpl s2 = new SaleTransactionImpl(2,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedSaleTransaction(s2);

        SaleTransactionImpl res = db.getSaleTransaction(3);
        assertSame(res, null);
    }

    @Test
    public void TestGetTransactionEntriesCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        SaleTransactionImpl s2 = new SaleTransactionImpl(2,0.5,10.0,"CLOSED");
        List<TicketEntry> tickets = new ArrayList<>();
        tickets.add(new TicketEntryImpl("123456789104","try",10,20.0,10.0));
        s1.setEntries(tickets);
        db.addClosedSaleTransaction(s1);
        db.addClosedSaleTransaction(s2);

        SaleTransactionImpl res = db.getSaleTransaction(1);
        assertSame(res.getTicketNumber(), s1.getTicketNumber());
        assertEquals(res.getEntries().get(0).getBarCode(),"123456789104");
    }
}
