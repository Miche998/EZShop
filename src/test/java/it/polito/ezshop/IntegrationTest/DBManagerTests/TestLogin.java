package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.UserImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestLogin {
    DBManager db = new DBManager();

    @Test
    public void TestLoginCorrect(){
        db.reset();
        Integer Uid = db.addUser("user","user","Administrator");
        UserImpl u = db.login("user","user");
        assertSame(u.getId(),Uid);
    }

    @Test
    public void TestLoginNoUserExisting(){
        db.reset();
        UserImpl u = db.login("user","user");
        assertNull(u);
    }

    @Test
    public void TestLoginWrongCredentials(){
        db.reset();
        Integer Uid = db.addUser("user","user","Administrator");
        UserImpl u = db.login("user","password");
        assertNull(u);
    }
}
