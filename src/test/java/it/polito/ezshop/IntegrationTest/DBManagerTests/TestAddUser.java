package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAddUser {
    DBManager db = new DBManager();

    @Test
    public void TestAddNewUser(){
        db.reset();
        Integer Uid = db.addUser("user","user","Administrator");
        assertSame(Uid,1);
        Integer Uid2 = db.addUser("user2","user","Administrator");
        assertSame(Uid2,2);
    }

    @Test
    public void TestExistingUser(){
        db.reset();
        Integer Uid = db.addUser("user","user","Administrator");
        Integer Uid2 = db.addUser("user","user","Administrator");
        assertSame(Uid2,-1);
    }

}
