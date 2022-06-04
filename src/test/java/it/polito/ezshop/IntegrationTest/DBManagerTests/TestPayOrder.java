package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPayOrder {
    DBManager db = new DBManager();

    @Test //DONE
    public void TestPayOrderCorrect(){
        db.reset();

        Integer pId = db.addProduct("description", "123456789104", 20.0, "note");
        Integer oId = db.issueOrder("123456789104",10,20.0);

        boolean check = db.payOrder(oId);

        assertTrue(check);

    }

    @Test //Order does not exist case DONE
    public void TestPayOrderNotExisting(){
        db.reset();

        boolean check = db.payOrder(1);
        assertFalse(check);

    }

    @Test //Order was not in an "ISSUED" state DONE
    public void TestPayOrderWrongNotIssued(){
        db.reset();

        Integer pId = db.addProduct("description", "123456789104", 20.0, "note");
        Integer oId = db.issueOrder("123456789104",10,20.0);

        db.payOrder(oId);
        boolean check = db.payOrder(oId);

        assertFalse(check);
    }

}
