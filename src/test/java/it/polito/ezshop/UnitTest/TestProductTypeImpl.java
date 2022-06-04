package it.polito.ezshop.UnitTest;

import it.polito.ezshop.model.ProductTypeImpl;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestProductTypeImpl {
    ProductTypeImpl p = new ProductTypeImpl(1, 10, "plocation","pnote","pdescription","00000000000001",23.00);
    @Test
    public void testSetQuantity(){
        p.setQuantity(10);
        assertSame(10,p.getQuantity());
    }
    @Test
    public void testSetLocation(){
        p.setLocation("Shell#5");
        assertEquals("Shell#5",p.getLocation());
    }
    @Test
    public void testSetNote(){
        p.setNote("Note2");
        assertEquals("Note2",p.getNote());
    }
    @Test
    public void testSetProductDescription(){
        p.setProductDescription("DescriptionExample");
        assertEquals("DescriptionExample",p.getProductDescription());
    }
    @Test
    public void testSetBarcode(){
        p.setBarCode("000000000000002");
        assertEquals("000000000000002",p.getBarCode());
    }
    @Test
    public void testSetPricePerUnit(){
        p.setPricePerUnit(12.00);
        assertEquals(12.00,p.getPricePerUnit(),0.00);
    }
    @Test
    public void testSetId(){
        p.setId(12);
        assertSame(12,p.getId());
    }
    @Test
    public void testModifyQuantity(){
        p.setQuantity(23);
        int toBeAdded = 10;
        p.modifyQuantity(toBeAdded);
        assertSame(33,p.getQuantity());
    }
}
