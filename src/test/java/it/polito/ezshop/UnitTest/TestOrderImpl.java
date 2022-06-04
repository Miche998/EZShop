package it.polito.ezshop.UnitTest;

import it.polito.ezshop.model.OrderImpl;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestOrderImpl {
    private OrderImpl o = new OrderImpl(1, "ABC",2.30, 4, "Payed", 3);

    @Test
    public void testSetProductCode(){
        o.setProductCode("DEF");
        String s = o.getProductCode();

        assertEquals("DEF", s);
    }

    @Test
    public void testSetPricePerUnit(){
        o.setPricePerUnit(3.40);
        double d = o.getPricePerUnit();

        assertEquals(3.40, d, 0.00);
    }

    @Test
    public void testSetQuantity(){
        o.setQuantity(5);
        Integer i = o.getQuantity();

        assertSame(5,i);
    }

    @Test
    public void testSetStatus(){
        o.setStatus("PAYED");
        String s = o.getStatus();

        assertEquals("PAYED", s);
    }

    @Test
    public void setBalanceId(){
        o.setBalanceId(67);
        Integer i = o.getBalanceId();

        assertSame(67, i);
    }

    @Test
    public void setOrderId(){
        o.setOrderId(34);
        Integer i = o.getOrderId();

        assertSame(34,i);
    }
}
