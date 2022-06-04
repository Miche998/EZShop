package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDeleteCustomer {
    DBManager db = new DBManager();
    @Test
    public void TestDeleteCustomerC(){
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        boolean check = db.deleteCustomer(Cid);
        assertTrue(check);
    }

    @Test
    public void TestDeleteCustomerW(){
        db.reset();
        boolean check = db.deleteCustomer(1);
        assertFalse(check);
    }

}