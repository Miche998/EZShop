package it.polito.ezshop.IntegrationTest.DBManagerTests;
import it.polito.ezshop.data.User;
import it.polito.ezshop.model.UserImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestGetUsers {
    DBManager db = new DBManager();
    @Test
    public void TestGetAllUsers(){
        db.reset();
        Integer Uid = db.addUser("user","user","Administrator");
        Integer Uid2 = db.addUser("user2","user","Administrator");
        List<User> users = new ArrayList<>();
        users = db.getAllUsers();
        assertSame(users.get(0).getId(),Uid);
        assertSame(users.get(1).getId(),Uid2);
    }
    @Test
    public void TestGetAllUsersEmpty(){
        db.reset();
        List<User> users = new ArrayList<>();
        users = db.getAllUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    public void TestGetUser(){
        db.reset();
        Integer Uid = db.addUser("user","user","Administrator");
        UserImpl user = db.getUser(Uid);
        assertSame(user.getId(),Uid);
    }

    @Test
    public void TestGetUserNull(){
        db.reset();
        UserImpl user = db.getUser(1);
        assertNull(user);
    }
}
