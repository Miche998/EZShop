package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestUpdateQuantity {
    DBManager db = new DBManager();

    @Test
    public void TestUpdateQuantityCorrect(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        Integer pTid2 = db.addProduct("product2","123456789111",15.0,"note2");
        Integer tobeAdded = 10;
        Integer oldQuantity = db.getProductByBarCode("123456789104").getQuantity();
        db.updatePosition(pTid,"1A1");
        db.updateQuantity(pTid,tobeAdded);
        assertSame(tobeAdded+oldQuantity,db.getProductByBarCode("123456789104").getQuantity());
    }

    @Test
    public void TestUpdateQuantityProductNotExists(){
        db.reset();
        int tobeAdded = 10;
        boolean check = db.updateQuantity(1,tobeAdded);
        assertFalse(check);
    }

    @Test
    public void TestUpdateQuantityNegativeQuantity(){
        db.reset();
        int tobeAdded = -10;
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        db.updatePosition(pTid,"1A1");
        boolean check = db.updateQuantity(pTid,tobeAdded);
        assertFalse(check);
    }

    @Test
    public void TestUpdateQuantityNoPosition(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        Integer pTid2 = db.addProduct("product2","123456789111",15.0,"note2");
        int tobeAdded = 10;
        db.updateQuantity(pTid,tobeAdded);
        assertEquals(tobeAdded,db.getProductByBarCode("123456789104").getQuantity()+tobeAdded);
    }

}
