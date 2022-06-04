package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestGetUser {
    EZShop ezShop = new EZShop();
    @Test
    public void TestGetUserCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        Integer uId = ezShop.createUser("user","user","Cashier");
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        User u = ezShop.getUser(uId);
        assertSame(1,uId);
    }

    @Test
    public void TestGetUserNullorNegativeId() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        assertThrows(InvalidUserIdException.class,()->ezShop.getUser(-1));
        assertThrows(InvalidUserIdException.class,()->ezShop.getUser(null));
    }

    @Test
    public void TestGetUserWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Cashier");
        ezShop.createUser("Mark","user","Cashier");
        assertThrows(UnauthorizedException.class,()->ezShop.getUser(2));
        ezShop.login("Luke","user");
        assertThrows(UnauthorizedException.class,()->ezShop.getUser(2));
    }

    @Test
    public void TestGetAllUserCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        Integer uId = ezShop.createUser("user","user","Cashier");
        Integer uId2= ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        List<User> users = new ArrayList<>();
        users = ezShop.getAllUsers();
        assertSame(1,users.get(0).getId());
        assertSame(2,users.get(1).getId());
    }

    @Test
    public void TestGetAllUserWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        Integer uId = ezShop.createUser("user","user","Cashier");
        Integer uId2= ezShop.createUser("Luke","user","Cashier");
        assertThrows(UnauthorizedException.class,()->ezShop.getAllUsers());
        ezShop.login("Luke","user");
        assertThrows(UnauthorizedException.class,()->ezShop.getAllUsers());
    }
}
