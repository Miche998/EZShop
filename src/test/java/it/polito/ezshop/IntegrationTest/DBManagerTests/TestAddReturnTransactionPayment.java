package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.ReturnTransactionImpl;
import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAddReturnTransactionPayment {
    DBManager db = new DBManager();

    @Test
    public void TestAddReturnCreditCardPayment(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedReturnTransaction(r1);

        db.addSaleTransactionPayment(1,"Credit Card", 5.00, "4485370086510891", null, null);
        boolean res = db.addReturnTransactionPayment(1,"Credit Card", 2.0, "4485370086510891");

        assertTrue(res);
        assertEquals("PAYED", db.getReturnTransaction(1).getStatus());

    }

    @Test
    public void TestAddReturnCashPayment(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedReturnTransaction(r1);

        db.addSaleTransactionPayment(1,"Credit Card", 5.00, "4485370086510891", null, null);
        boolean res = db.addReturnTransactionPayment(1,"Cash", 2.0, null);

        assertTrue(res);
        assertEquals("PAYED", db.getReturnTransaction(1).getStatus());

    }
}
