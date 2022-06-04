package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestUpdateUserRights {
    DBManager db = new DBManager();

    @Test
    public void TestUpdateUserRightsCorrect(){
        db.reset();
        Integer Uid = db.addUser("user","user","Administrator");
        db.updateUserRights(Uid,"Cashier");
        assertEquals(db.getUser(Uid).getRole(),"Cashier");
    }

    @Test
    public void TestUpdateUserRightsWrong(){
        db.reset();
        boolean check = db.updateUserRights(1,"Cashier");
        assertFalse(check);
    }
}
