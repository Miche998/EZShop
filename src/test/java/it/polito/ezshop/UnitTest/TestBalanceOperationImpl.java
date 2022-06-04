package it.polito.ezshop.UnitTest;

import it.polito.ezshop.model.BalanceOperationImpl;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;


public class TestBalanceOperationImpl {
    BalanceOperationImpl balanceOp = new BalanceOperationImpl("DEBIT",50.00, java.time.LocalDate.now(),1);
    @Test
    public void testSetBalanceId(){
        balanceOp.setBalanceId(3);
        assertSame(balanceOp.getBalanceId(),3);
    }

    @Test
    public void testSetDate(){
        balanceOp.setDate(LocalDate.parse("2021-06-22"));

        assertTrue(balanceOp.getDate().isEqual(LocalDate.parse("2021-06-22")));
    }

    @Test
    public void testSetMoney(){
        balanceOp.setMoney(120.00);

        assertEquals(120.00,balanceOp.getMoney(),0.00);
    }

    @Test
    public void testSetType(){
        balanceOp.setType("CREDIT");
        assertEquals("CREDIT",balanceOp.getType());
    }

}
