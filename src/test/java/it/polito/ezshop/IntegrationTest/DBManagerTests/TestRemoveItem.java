package it.polito.ezshop.IntegrationTest.DBManagerTests;



import it.polito.ezshop.model.ProductImpl;
import it.polito.ezshop.util.DBManager;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestRemoveItem {
    DBManager db = new DBManager();

    @Test
    public void TestRemoveItem(){
        db.reset();
        ProductImpl p = new ProductImpl(1L,"0123456789111");
        ProductImpl p2 = new ProductImpl(2L,"0123456789128");
        assertFalse(db.removeItem(p));
        db.addItem(p);
        assertFalse(db.addItem(p));
        db.addItem(p2);
        db.removeItem(p);
        boolean check = db.checkRFID(1L);
        assertTrue(check);
        assertFalse(db.checkRFID(p2.getRFID()));


    }
}
