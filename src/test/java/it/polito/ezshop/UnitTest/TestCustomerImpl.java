package it.polito.ezshop.UnitTest;

import it.polito.ezshop.model.CustomerImpl;
import it.polito.ezshop.model.LoyaltyCardImpl;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCustomerImpl {
    private CustomerImpl c = new CustomerImpl("Andrea", 1,null);

    @Test
    public void testSetCustomerName(){
        c.setCustomerName("Enrico");
        String s = c.getCustomerName();

        assertEquals("Enrico", s);
    }

    @Test
    public void testSetCustomerCard(){
        LoyaltyCardImpl lc = new LoyaltyCardImpl("1234567891", 0);
        c.setLoyaltyCard(lc);

        c.setCustomerCard("1234567891");
        String s = c.getCustomerCard();

        assertEquals("1234567891", s);
    }

    @Test
    public void testSetId(){
        c.setId(3);
        Integer i = c.getId();

        assertSame( 3, i);
    }

    @Test
    public void testSetPoints(){
        LoyaltyCardImpl lc = new LoyaltyCardImpl("1234567891", 0);
        c.setLoyaltyCard(lc);

        c.setPoints(30);
        Integer i = c.getPoints();

        assertSame(30,i);
    }

    @Test
    public void testSetLoyaltyCard(){
        LoyaltyCardImpl lc = new LoyaltyCardImpl("1234567891", 0);
        c.setLoyaltyCard(lc);

        assertSame(c.getLoyaltyCard(), lc);
    }

}
