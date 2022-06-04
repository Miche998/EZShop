package it.polito.ezshop.UnitTest;

import it.polito.ezshop.model.ProductImpl;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestProductImpl {
    ProductImpl p = new ProductImpl(1L,"0123456789104");

    @Test
    public void testSetRFID(){
        p.setRFID(2L);
        assertSame(2L,p.getRFID());
    }

    @Test
    public void testSetBarCode(){
        p.setBarCode("0123456789111");
        assertSame("0123456789111",p.getBarCode());
    }

}
