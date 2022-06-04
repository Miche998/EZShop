package it.polito.ezshop.UnitTest;


import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.ReturnTransactionImpl;

import it.polito.ezshop.model.TicketEntryImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestReturnTransactionImpl {
    ReturnTransactionImpl rtrans = new ReturnTransactionImpl(1,2, 30.00,"UNPAYED");

    @Test
    public void testSetReturnId(){
        rtrans.setReturnId(1);
        Integer rId = rtrans.getReturnId();

        assertSame(1,rId);
    }

    @Test
    public void testSetTransactionId(){
        rtrans.setTransactionId(2);
        Integer tId = rtrans.getTransactionId();

        assertSame(2,tId);
    }

    @Test
    public void testSetPrice(){
        rtrans.setPrice(40.00);
        double p = rtrans.getPrice();

        assertEquals(40.00,p,0.00);
    }

    @Test
    public void testSetStatus(){
        rtrans.setStatus("PAID");
        String status = rtrans.getStatus();

        assertEquals("PAID",status);
    }

    @Test
    public void testSetEntries(){

        TicketEntryImpl ticket = new TicketEntryImpl("00000000000001","description",5,10.00,0.20);
        TicketEntryImpl ticket2 = new TicketEntryImpl("00000000000002","description2",10,15.00,0.10);

        List<TicketEntry> ticketList = rtrans.getEntries();

        ticketList.add(ticket);
        ticketList.add(ticket2);

        rtrans.setEntries(ticketList);

        assertEquals(rtrans.getEntries(),ticketList);
    }

    @Test
    public void testComputePrice(){

        double pricePerUnit = 15.00;
        int amount = 5;
        double discountRate = 0.30; //30% of discount
        double finalPrice = (pricePerUnit*amount)*(1-discountRate);

        TicketEntryImpl ticket = new TicketEntryImpl("00000000000001","testProduct",amount,pricePerUnit,discountRate);

        List<TicketEntry> ticketList = rtrans.getEntries();
        ticketList.add(ticket);

        rtrans.computePrice();
        double finalPriceToCheck = rtrans.getPrice();

        assertEquals(finalPrice,finalPriceToCheck,0.00);

    }

}
