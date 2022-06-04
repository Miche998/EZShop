package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.OrderImpl;
import it.polito.ezshop.model.ProductTypeImpl;
import it.polito.ezshop.util.DBManager;
import static org.junit.Assert.*;
import org.junit.Test;


public class TestGetProductFromOrder {
    DBManager db = new DBManager();

    @Test
    public void TestGetProductFromOrder(){
        db.reset();

        Integer pId = db.addProduct("description", "123456789104", 20.0, "note");
        Integer oId = db.issueOrder("123456789104",10,20.0);

        ProductTypeImpl p = db.getProductFromOrder(1);

        assertSame(pId, p.getId());

    }
}
