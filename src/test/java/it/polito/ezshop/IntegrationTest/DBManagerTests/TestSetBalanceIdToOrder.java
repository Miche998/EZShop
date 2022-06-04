package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.data.Order;
import it.polito.ezshop.model.OrderImpl;
import it.polito.ezshop.model.ProductTypeImpl;
import it.polito.ezshop.util.DBManager;
import static org.junit.Assert.*;
import org.junit.Test;


public class TestSetBalanceIdToOrder {
    DBManager db = new DBManager();
    
    @Test //DONE
    public void testSetBalanceIdToOrderWrong(){
        db.reset();

       assertFalse(db.setBalanceIdToOrder(1,1));
    }

    @Test //DONE
    public void testSetBalanceIdToOrderCorrect(){
        db.reset();
        Integer bId = 3;

        Integer pId = db.addProduct("description", "123456789104", 20.0, "note");
        Integer oId = db.issueOrder("123456789104",10,20.0);

        boolean check = db.setBalanceIdToOrder(oId, bId);

        assertSame(bId, db.getOrder(oId).getBalanceId());
    }
}
