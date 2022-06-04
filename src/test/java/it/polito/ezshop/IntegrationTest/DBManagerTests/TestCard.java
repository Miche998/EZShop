package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCard {
    DBManager db = new DBManager();
    @Test
    public void TestAttachCardToCustomerCorrect(){
        db.reset();
        Integer cId = db.addCustomer("Luke");
        db.addCard("123456789007");
        boolean check = db.attachCardToCustomer("123456789007",cId);
        assertTrue(check);
    }

    @Test
    public void TestAttachCardToCustomerWrongSameCard(){
        db.reset();
        Integer cId = db.addCustomer("Luke");
        db.addCard("123456789007");
        Integer cId2 = db.addCustomer("Mary");
        db.attachCardToCustomer("123456789007",cId);
        boolean check = db.attachCardToCustomer("123456789007",cId2);
        assertFalse(check);
    }
    @Test
    public void TestAttachCardToCustomerWrongCustomerId(){
        db.reset();
        Integer cId = db.addCustomer("Luke");
        db.addCard("123456789007");
        boolean check = db.attachCardToCustomer("123456789007",2);
        assertFalse(check);
    }

    @Test
    public void TestAddCard(){
        db.reset();
        boolean check = db.addCard("123456789007");
        assertTrue(check);
    }

    @Test
    public void TestModifyPointsOnCardCorrect(){
        db.reset();
        Integer cId = db.addCustomer("Luke");
        db.addCard("123456789007");
        db.attachCardToCustomer("123456789007",cId);
        int p = db.getCustomer(cId).getPoints();
        db.modifyPointsOnCard("123456789007",10);
        int finalPoints = db.getCustomer(cId).getPoints();
        assertEquals(finalPoints,(p+10));
    }

    @Test
    public void TestModifyPointsOnCardMissingCard(){
        db.reset();
        boolean check = db.modifyPointsOnCard("123456789007",10);
        assertFalse(check);
    }

    @Test
    public void TestModifyPointsOnCardNegativePoints(){
        db.reset();
        Integer cId = db.addCustomer("Luke");
        db.addCard("123456789007");
        db.attachCardToCustomer("123456789007",cId);
        boolean check = db.modifyPointsOnCard("123456789007",-10);
        assertFalse(check);
    }

}
