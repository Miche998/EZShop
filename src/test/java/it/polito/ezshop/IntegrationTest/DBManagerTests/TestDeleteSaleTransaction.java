package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestDeleteSaleTransaction {

    DBManager db = new DBManager();

    @Test
    public void TestDeleteTransactionCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        boolean res = db.deleteSaleTransaction(1);
        assertTrue(res);
    }

    @Test
    public void TestDeleteTransactionNotExistent(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        boolean res = db.deleteSaleTransaction(2);
        assertFalse(res);
    }

    @Test
    public void TestDeleteTransactionPayed(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addSaleTransactionPayment(1,"Cash",s1.getPrice(),null,5.0,0.0);
        boolean res = db.deleteSaleTransaction(1);
        assertFalse(res);
    }
}
