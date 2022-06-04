package it.polito.ezshop.IntegrationTest.DBManagerTests;
import it.polito.ezshop.data.Order;
import it.polito.ezshop.model.OrderImpl;
import it.polito.ezshop.model.ProductTypeImpl;
import it.polito.ezshop.util.DBManager;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestGetAllOrders{
    DBManager db = new DBManager();

    @Test //DONE
    public void TestGetAllOrdersCorrect(){
        db.reset();

        Integer pId = db.addProduct("description", "123456789104", 20.0, "note");
        Integer oId = db.issueOrder("123456789104",10,20.0);

        Integer pId2 = db.addProduct("description2", "123456789180", 20.0, "note2");
        Integer oId2 = db.issueOrder("123456789180",10,20.0);

        List<Order> orders = new ArrayList<>();
        orders = db.getAllOrders();

        assertSame(orders.get(0).getOrderId(), oId);
        assertSame(orders.get(1).getOrderId(), oId2);

    }

    @Test
    public void TesGetAllOrdersWrong(){
        db.reset();

        List<Order> orders = new ArrayList<>();

        assertTrue(orders.isEmpty());
    }
}
