package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAddProduct {
    DBManager db = new DBManager();

    @Test
    public void TestAddProductWorking(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        assertSame(pTid,1);
        Integer pTid2 = db.addProduct("product2","123456789111",15.0,"note2");
        assertSame(pTid2,2);
    }

    @Test
    public void TestAddProductSameBarcode(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        Integer pTid2 = db.addProduct("product1","123456789104",15.0,"note");
        assertSame(pTid2,-1);
    }
}
