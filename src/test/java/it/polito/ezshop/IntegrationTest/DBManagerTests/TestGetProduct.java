package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.model.ProductTypeImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestGetProduct {
    DBManager db = new DBManager();
    @Test
    public void TestGetAllProducts(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        Integer pTid2 = db.addProduct("product2","123456789111",15.0,"note2");
        List<ProductType> products = new ArrayList<>();
        products = db.getAllProducts();
        assertSame(products.get(0).getId(),pTid);
        assertSame(products.get(1).getId(),pTid2);
    }
    @Test
    public void TestGetAllProductsEmpty(){
        db.reset();
        List<ProductType> products = new ArrayList<>();
        products = db.getAllProducts();
        assertTrue(products.isEmpty());
    }

    @Test
    public void TestGetProductByBarCode(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        ProductTypeImpl p = db.getProductByBarCode("123456789104");
        assertSame(p.getId(),pTid);
    }

    @Test
    public void TestGetProductByBarCodeNull(){
        db.reset();
        ProductTypeImpl p = db.getProductByBarCode("123456789104");
        assertNull(p);
    }

    @Test
    public void TestGetProductByDescription(){
        db.reset();
        Integer pTid = db.addProduct("product1","123456789104",15.0,"note");
        Integer pTid2 = db.addProduct("product2","123456789111",15.0,"note2");
        List <ProductType> p = db.getProductByDescription("product");
        assertSame(p.get(0).getId(),pTid);
        assertSame(p.get(1).getId(),pTid2);
    }

    @Test
    public void TestGetProductByDescriptionNull(){
        db.reset();
        Integer pTid = db.addProduct("description","123456789104",15.0,"note");
        List <ProductType> p = db.getProductByDescription("product");
        assertTrue(p.isEmpty());
    }
}
