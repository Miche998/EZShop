package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.OrderImpl;
import it.polito.ezshop.model.ProductTypeImpl;
import it.polito.ezshop.util.DBManager;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestGetOrder {
    DBManager db = new DBManager();

    @Test
    public void TestGetOrderCorrect(){
        db.reset();

        Integer pId = db.addProduct("description", "123456789104", 20.0, "note");
        Integer oId = db.issueOrder("123456789104",10,20.0);
        OrderImpl o = db.getOrder(1);

        assertSame(oId, o.getOrderId());

    }

    @Test
    public void TestGetOrderWrong(){
        db.reset();

        OrderImpl o = db.getOrder(1);

        assertNull(o);
    }
}
