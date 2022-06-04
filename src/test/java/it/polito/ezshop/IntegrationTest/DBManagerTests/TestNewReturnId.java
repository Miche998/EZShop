package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.*;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class TestNewReturnId {
    DBManager db = new DBManager();

    @Test
    public void TestNewReturnId(){
        db.reset();

        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,5.0,"CLOSED");


        db.addClosedSaleTransaction(s1);
        db.addClosedReturnTransaction(r1);

        Integer i = db.getNewReturnTransactionId();
        assertSame(2, i);
    }

    @Test
    public void TestNewReturnIdZero(){
        db.reset();

        Integer i = db.getNewReturnTransactionId();
        assertSame(1, i);
    }
}
