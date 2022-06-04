package it.polito.ezshop.UnitTest;

import it.polito.ezshop.model.TicketEntryImpl;
import org.junit.Test;

import static org.junit.Assert.*;


public class TestTicketEntryImpl {
    TicketEntryImpl ticket = new TicketEntryImpl("000000000001","description...",5,15.00,0.0);
    @Test
    public void testTicketSetBarCode(){

        ticket.setBarCode("000000000002");
        String bc = ticket.getBarCode();

        assertEquals("000000000002",bc);

    }
    @Test
    public void testTicketSetProductDescription(){

        ticket.setProductDescription("new description");
        String descr = ticket.getProductDescription();

        assertEquals("new description",descr);

    }
    @Test
    public void testTicketSetAmount(){

        ticket.setAmount(3);
        int amount = ticket.getAmount();

        assertEquals(3,amount);
    }
    @Test
    public void testTicketSetDiscountRate(){

        ticket.setDiscountRate(0.10);
        double dr = ticket.getDiscountRate();

        assertEquals(0.10,dr,0.00);
    }

    @Test
    public void testTicketSetPricePerUnit(){

        ticket.setPricePerUnit(18.00);
        double dr = ticket.getPricePerUnit();

        assertEquals(18.00,dr,0.00);
    }


}
