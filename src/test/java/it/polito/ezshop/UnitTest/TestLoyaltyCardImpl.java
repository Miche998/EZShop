package it.polito.ezshop.UnitTest;

import it.polito.ezshop.model.LoyaltyCardImpl;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestLoyaltyCardImpl {
    private LoyaltyCardImpl lc = new LoyaltyCardImpl("ABCDE", 0);

    @Test
    public void testSetCardCode(){

        lc.setCardCode("ARHG");
        String s = lc.getCardCode();

        assertEquals("ARHG",s);

    }

    @Test
    public void testSetPoints(){

        lc.setPoints(32);
        Integer i = lc.getPoints();

        assertSame(32,i);
    }

}
