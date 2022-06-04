package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestUpdatePosition {
    DBManager db = new DBManager();
    @Test
    public void TestUpdatePositionCorrect(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        boolean check = db.updatePosition(pTid,"1A1");
       assertTrue(check);
    }

    @Test
    public void TestUpdatePositionProductNotExists(){
        db.reset();
        boolean check = db.updatePosition(1,"1A1");
        assertFalse(check);
    }

    @Test
    public void TestUpdatePositionOccupied(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        Integer pTid2 = db.addProduct("product2","123456789111",15.0,"note2");
        db.updatePosition(pTid,"1A1");
        boolean check = db.updatePosition(pTid2,"1A1");
        assertFalse(check);
    }
}
