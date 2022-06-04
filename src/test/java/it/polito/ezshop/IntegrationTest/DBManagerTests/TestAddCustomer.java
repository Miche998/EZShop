package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAddCustomer {
    DBManager db = new DBManager();

    @Test
    public void TestAddNewCustomer(){
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        assertSame(Cid,1);
        Integer Cid2 = db.addCustomer("Gianluigi");
        assertSame(Cid2,2);
    }

    @Test
    public void TestExistingCustomer(){
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        Integer Cid2 = db.addCustomer("Zlatan");
        assertSame(Cid2,-1);
    }

}
