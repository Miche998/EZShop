package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.model.TicketEntryImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestAddClosedSaleTransaction {
    DBManager db = new DBManager();

    @Test
    public void TestAddTransactionCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        SaleTransactionImpl s2 = new SaleTransactionImpl(2,0.5,10.0,"CLOSED");

        boolean res1 = db.addClosedSaleTransaction(s1);
        boolean res2 = db.addClosedSaleTransaction(s2);

        assertTrue(res1);
        assertTrue(res2);
    }

    @Test
    public void TestAddTransactionCorrectList(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        List<TicketEntry> tickets = new ArrayList<>();
        tickets.add(new TicketEntryImpl("123456789104","try",10,20.0,10.0));
        s1.setEntries(tickets);
        boolean res1 = db.addClosedSaleTransaction(s1);
        assertTrue(res1);
    }

    @Test
    public void TestAddTransactionWrong(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        SaleTransactionImpl s2 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        boolean res1 = db.addClosedSaleTransaction(s1);
        boolean res2 = db.addClosedSaleTransaction(s2);

        assertFalse(res2);
    }
}
