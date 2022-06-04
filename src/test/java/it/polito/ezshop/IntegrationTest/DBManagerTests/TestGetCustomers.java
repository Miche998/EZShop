package it.polito.ezshop.IntegrationTest.DBManagerTests;
import it.polito.ezshop.data.Customer;
import it.polito.ezshop.model.CustomerImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestGetCustomers {
    DBManager db = new DBManager();
    @Test
    public void TestGetAllCustomers(){
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        Integer Cid2 = db.addCustomer("Romelu");
        List<Customer> customers = new ArrayList<>();
        customers = db.getAllCustomers();
        assertSame(customers.get(0).getId(),Cid);
        assertSame(customers.get(1).getId(),Cid2);
    }

    @Test
    public void TestGetAllCustomersWithCard(){
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        db.addCard("123456789007");
        db.attachCardToCustomer("123456789007",Cid);
        Integer Cid2 = db.addCustomer("Luke");
        List<Customer> customers = new ArrayList<>();
        customers = db.getAllCustomers();
        assertSame(customers.get(0).getId(),Cid);
        assertSame(customers.get(1).getId(),Cid2);
    }
    @Test
    public void TestGetAllCustomersEmpty(){
        db.reset();
        List<Customer> customers = new ArrayList<>();
        customers = db.getAllCustomers();
        assertTrue(customers.isEmpty());
    }

    @Test
    public void TestGetCustomer(){
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        CustomerImpl customer = db.getCustomer(Cid);
        assertSame(customer.getId(),Cid);
    }

    @Test
    public void TestGetCustomerNull(){
        db.reset();
        CustomerImpl customer = db.getCustomer(1);
        assertNull(customer);
    }

    @Test
    public void TestGetCustomerWithCard(){
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        db.addCard("123456789007");
        db.attachCardToCustomer("123456789007",Cid);
        CustomerImpl customer = db.getCustomer(1);
        assertSame(customer.getId(),Cid);
    }

}
