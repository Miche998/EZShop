package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.ProductImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGetProductRFID {
    DBManager db = new DBManager();

    @Test
    public void TestGetProductRFIDCorrect(){
        db.reset();
        Integer pId = db.addProduct("descr","0123456789111",10.0,"note");
        db.updatePosition(pId,"1-A-1");
        Integer oId = db.issueOrder("0123456789111",5,5.0);
        db.payOrder(oId);
        db.recordOrderArrivalRFID(1,1L);

        ProductImpl p = db.getProductByRFID(1L);
        ProductImpl p2 = db.getProductByRFID(2L);
        assertSame(1L,p.getRFID());
        assertSame(2L,p2.getRFID());
        ProductImpl p6 = db.getProductByRFID(6L);
        assertNull(p6);
    }
}
