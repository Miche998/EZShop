package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.ReturnTransactionImpl;
import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.model.TicketEntryImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestAddClosedReturnTransaction {

    DBManager db = new DBManager();

    @Test
    public void TestAddReturnTransactionCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        boolean res = db.addClosedReturnTransaction(r1);

        assertTrue(res);
    }

    @Test
    public void TestAddReturnTransactionWrong(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");
        ReturnTransactionImpl r2 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedReturnTransaction(r1);
        boolean res = db.addClosedReturnTransaction(r2);

        assertFalse(res);
    }

    @Test
    public void TestAddReturnTransactionEntriesCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");

        List<TicketEntry> tickets = new ArrayList<>();
        tickets.add(new TicketEntryImpl("123456789104","try",10,20.0,10.0));
        s1.setEntries(tickets);
        r1.setEntries(tickets);
        db.addClosedSaleTransaction(s1);
        boolean res = db.addClosedReturnTransaction(r1);
        assertEquals(r1.getEntries().get(0).getBarCode(),"123456789104");

        assertTrue(res);
    }

}
