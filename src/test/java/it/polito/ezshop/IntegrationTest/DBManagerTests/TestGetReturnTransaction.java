package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.ReturnTransactionImpl;
import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.model.TicketEntryImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;

public class TestGetReturnTransaction {

    DBManager db = new DBManager();

    @Test
    public void TestGetReturnTransactionCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedReturnTransaction(r1);

        ReturnTransactionImpl res = db.getReturnTransaction(1);
        assertSame(res.getReturnId(), r1.getReturnId());
    }

    @Test
    public void TestGetReturnTransactionEntriesCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");
        List<TicketEntry> tickets = new ArrayList<>();
        tickets.add(new TicketEntryImpl("123456789104","try",10,20.0,10.0));
        s1.setEntries(tickets);
        r1.setEntries(tickets);
        db.addClosedSaleTransaction(s1);
        db.addClosedReturnTransaction(r1);

        ReturnTransactionImpl res = db.getReturnTransaction(1);
        assertSame(res.getReturnId(), r1.getReturnId());
    }

    @Test
    public void TestGetReturnTransactionNotExistent(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedReturnTransaction(r1);

        ReturnTransactionImpl res = db.getReturnTransaction(2);
        assertSame(res, null);
    }
}
