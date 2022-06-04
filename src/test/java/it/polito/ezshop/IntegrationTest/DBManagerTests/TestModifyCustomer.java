package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.CustomerImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestModifyCustomer {
    DBManager db = new DBManager();

    @Test
    public void TestModifyCustomerNameAndCardCorrect(){
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        db.addCard("123456789007");
        db.attachCardToCustomer("123456789007",1);
        db.addCard("123456789015");
        boolean check = db.modifyCustomer(Cid, "Luke", "123456789015");
        assertTrue(check);
    }

    @Test
    public void TestModifyCustomerNameAndCardWrong(){
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        db.addCard("123456789007");
        db.attachCardToCustomer("123456789007", Cid);
        Integer Cid2 = db.addCustomer("Luke");
        db.addCard("123456789015");
        db.attachCardToCustomer("123456789015", Cid2);
        boolean check = db.modifyCustomer(Cid, "Zlatana", "123456789015");
        assertFalse(check);
    }

    @Test
    public void TestModifyCustomerNameAndCardEmptyString() {
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        db.modifyCustomer(Cid, "Luke","");
        assertNull(db.getCustomer(Cid).getCustomerCard());
    }

    @Test
    public void TestModifyCustomerNameAndCardNull() {
        db.reset();
        Integer Cid = db.addCustomer("Zlatan");
        db.modifyCustomer(Cid, "Luke",null);
        assertNull(db.getCustomer(Cid).getCustomerCard());
    }

}