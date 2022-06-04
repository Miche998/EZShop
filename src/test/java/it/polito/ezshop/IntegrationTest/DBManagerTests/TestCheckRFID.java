package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.ProductImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCheckRFID {
    DBManager db = new DBManager();

    @Test
    public void TestCheckRFIDCorrect(){
        db.reset();
        ProductImpl p = new ProductImpl(1L,"0123456789111");
        assertTrue(db.checkRFID(p.getRFID()));
        db.addItem(p);
        assertFalse(db.checkRFID(1L));
    }
}
