package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.ReturnTransactionImpl;
import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestDeleteReturnTransaction {

    DBManager db = new DBManager();

    @Test
    public void TestDeleteReturnTransactionCorrect(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedReturnTransaction(r1);
        boolean res = db.deleteReturnTransaction(1);
        assertTrue(res);
    }

    @Test
    public void TestDeleteReturnTransactionNotExistent(){
        db.reset();

        boolean res = db.deleteReturnTransaction(1);
        assertFalse(res);
    }

    @Test
    public void TestDeleteReturnTransactionPayed(){
        db.reset();
        SaleTransactionImpl s1 = new SaleTransactionImpl(1,0.5,10.0,"CLOSED");
        ReturnTransactionImpl r1 = new ReturnTransactionImpl(1,1,2.0,"CLOSED");

        db.addClosedSaleTransaction(s1);
        db.addClosedReturnTransaction(r1);
        db.addSaleTransactionPayment(1,"Cash",s1.getPrice(),null,5.0,0.0);
        db.addReturnTransactionPayment(1,"Cash",s1.getPrice(),null);
        boolean res = db.deleteReturnTransaction(1);
        assertFalse(res);
    }
}
