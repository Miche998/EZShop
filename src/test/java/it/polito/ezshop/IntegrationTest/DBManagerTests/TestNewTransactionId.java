package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class TestNewTransactionId {

    DBManager db = new DBManager();

    @Test
    public void TestNewTransactionId(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        SaleTransactionImpl s2 = new SaleTransactionImpl(2,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedSaleTransaction(s2);

        Integer i = db.getNewTransactionId();
        assertSame(3, i);
    }

    @Test
    public void TestNewTransactionIdZero(){
        db.reset();

        Integer i = db.getNewTransactionId();
        assertSame(1, i);
    }
}
