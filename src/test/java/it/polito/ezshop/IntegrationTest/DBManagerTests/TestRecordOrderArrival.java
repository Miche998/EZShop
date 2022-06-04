package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.OrderImpl;
import it.polito.ezshop.model.ProductTypeImpl;
import it.polito.ezshop.util.DBManager;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestRecordOrderArrival {
    DBManager db = new DBManager();

    @Test //Order doesn't exist DONE
    public void TestRecordOrderArrivalNotExisting(){
        db.reset();
        OrderImpl o = db.getOrder(1);

        assertNull(o);
    }

    @Test //Order has status different from "PAYED" DONE
    public void TestRecordOrderArrivalNotPayed(){
        db.reset();

        Integer pId = db.addProduct("description", "123456789104", 20.0, "note");
        Integer oId = db.issueOrder("123456789104",10,20.0);

        boolean check = db.recordOrderArrival(oId);

        assertFalse(check);
    }

    @Test //DONE
    public void TestRecordOrderArrivalCorrect(){
        db.reset();

        Integer pId = db.addProduct("description", "123456789104", 20.0, "note");
        Integer oId = db.issueOrder("123456789104",10,20.0);
        boolean check = db.payOrder(oId);

        boolean check2 = db.recordOrderArrival(oId);

        assertTrue(check2);
    }
}
