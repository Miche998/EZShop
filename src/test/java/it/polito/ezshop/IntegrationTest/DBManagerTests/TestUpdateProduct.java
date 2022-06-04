package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestUpdateProduct {
    DBManager db = new DBManager();

    @Test
    public void TestUpdateProductCorrect(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        assertSame(pTid,1);
        boolean check = db.updateProduct(1,"product2","123456789111",20.0,"note");
        assertTrue(check);
    }
    @Test
    public void TestUpdateProductWrongId(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        boolean check = db.updateProduct(2,"product2","123456789111",20.0,"note");
        assertFalse(check);
    }
    @Test
    public void TestUpdateProductWrongBarcode(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        Integer pTid2 = db.addProduct("product2","123456789111",15.0,"note2");
        boolean check = db.updateProduct(2,"product2new","123456789104",20.0,"note_new");
        assertFalse(check);
    }
}
