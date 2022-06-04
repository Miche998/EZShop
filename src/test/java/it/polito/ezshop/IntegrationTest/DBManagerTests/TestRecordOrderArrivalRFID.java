package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestRecordOrderArrivalRFID {
    DBManager db = new DBManager();

     @Test
    public void TestRecordOrderArrivalRFIDCorrect(){
         db.reset();
         Integer pId = db.addProduct("descr","0123456789111",10.0,"note");
         db.updatePosition(pId,"1-A-1");
         boolean check = db.recordOrderArrivalRFID(1,1L);
         assertFalse(check);
         Integer oId = db.issueOrder("0123456789111",5,5.0);
         check = db.recordOrderArrivalRFID(1,1L);
         assertFalse(check);
         db.payOrder(oId);
          check = db.recordOrderArrivalRFID(1,1L);
         assertTrue(check);
     }
}
