package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDeleteUser {
    DBManager db = new DBManager();
    @Test
    public void TestDeleteUserCorrect(){
        db.reset();
        Integer Uid = db.addUser("user","user","Administrator");
        boolean check = db.deleteUser(Uid);
        assertTrue(check);
    }

    @Test
    public void TestDeleteUserWrong(){
        db.reset();
        boolean check = db.deleteUser(1);
        assertFalse(check);
    }

}
