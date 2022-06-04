package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDeleteProduct {
    DBManager db = new DBManager();

    @Test
    public void TestDeleteProductCorrect(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        boolean check = db.deleteProduct(pTid);
        assertTrue(check);
    }

    @Test
    public void TestDeleteProductWrong(){
        db.reset();
        boolean check = db.deleteUser(1);
        assertFalse(check);
    }
}
