package it.polito.ezshop.UnitTest;

import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.SaleTransactionImpl;
import it.polito.ezshop.model.TicketEntryImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestSaleTransactionImpl {
    SaleTransactionImpl sale = new SaleTransactionImpl(5,0.0,0.0,"OPEN");

    @Test
    public void testSetTicketNumber(){
        sale.setTicketNumber(10);
        Integer tn = sale.getTicketNumber();

        assertSame(tn,10);
    }

    @Test
    public void testSetEntries(){

        TicketEntryImpl ticket = new TicketEntryImpl("00000000000001","description",5,10.00,0.20);
        TicketEntryImpl ticket2 = new TicketEntryImpl("00000000000002","description2",10,15.00,0.10);

        List<TicketEntry> ticketList = sale.getEntries();

        ticketList.add(ticket);
        ticketList.add(ticket2);

        sale.setEntries(ticketList);

        assertEquals(sale.getEntries(),ticketList);
    }

    @Test
    public void testSetDiscountRate(){
        sale.setDiscountRate(0.10);
        double dr = sale.getDiscountRate();
        assertEquals(0.10,dr,0.00);
    }

    @Test
    public void testSetPrice(){
        sale.setPrice(100);
        double p = sale.getPrice();
        assertEquals(100,p,0.00);
    }

    @Test
    public void testSetStatus(){
        sale.setStatus("CLOSED");
        String status = sale.getStatus();
        assertEquals("CLOSED",status);
    }

    @Test
    public void testComputePrice(){

        double pricePerUnit = 15.00;
        int amount = 5;
        double discountRate = 0.30; //30% of discount
        double finalPrice = (pricePerUnit*amount)*(1-discountRate);

        TicketEntryImpl ticket = new TicketEntryImpl("00000000000001","testProduct",amount,pricePerUnit,discountRate);

        List<TicketEntry> ticketList = sale.getEntries();
        ticketList.add(ticket);

        sale.computePrice();
        double finalPriceToCheck = sale.getPrice();

        assertEquals(finalPrice,finalPriceToCheck,0.00);

    }

    @Test
    public void testModifyEntryByBarcode(){


        TicketEntryImpl ticket = new TicketEntryImpl("00000000000001","testProduct",5,10.00,0.00);
        TicketEntryImpl ticket2 = new TicketEntryImpl("00000000000002","testProduct2",7,15.00,0.00);
        List<TicketEntry> ticketList = sale.getEntries();
        ticketList.add(ticket);
        ticketList.add(ticket2);

        int amountToModify = 3; //5-3 = 2

        sale.modifyEntryByBarcode("00000000000001",-amountToModify);
        int amountDecreased = ticket.getAmount();
        assertTrue(2 == amountDecreased);
        //assertEquals(2,amountDecreased);

    }

}
