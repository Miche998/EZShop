package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAddSaleTransactionPayment {
    DBManager db = new DBManager();

    @Test
    public void TestAddCreditCardPayment(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        boolean res = db.addSaleTransactionPayment(1,"Credit Card", 5.00, "4485370086510891", null, null);
        assertTrue(res);
        assertEquals("PAYED", db.getSaleTransaction(1).getStatus());


    }

    @Test
    public void TestAddCashPayment(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        boolean res = db.addSaleTransactionPayment(1,"Cash", 5.00, null, 10.0, 5.0);
        assertTrue(res);
        assertEquals("PAYED", db.getSaleTransaction(1).getStatus());

    }
}
