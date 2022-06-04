package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCreateUser {
    EZShop ezShop = new EZShop();

    @Test
    public void TestCreateUserCorrect() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Uid = ezShop.createUser("Mark","user","Cashier");
        assertSame(2,Uid);
    }
    @Test
    public void TestCreateUserExistingUser() throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
        ezShop.reset();
        Integer Cid = ezShop.createUser("Mark","user","Administrator");
        Integer Cid2 = ezShop.createUser("Mark","user","Administrator");
        assertSame(1,Cid);
        assertSame(-1,Cid2);
    }

    @Test
    public void TestCreateUserNullOrEmptyName() {
        assertThrows(InvalidUsernameException.class, ()-> ezShop.createUser("","user","Administrator"));
        assertThrows(InvalidUsernameException.class, ()-> ezShop.createUser(null,"user","Administrator"));
    }

    @Test
    public void TestCreateUserNullOrEmptyPassword() {
        assertThrows(InvalidPasswordException.class, ()-> ezShop.createUser("Mark","","Administrator"));
        assertThrows(InvalidPasswordException.class, ()-> ezShop.createUser("Mark",null,"Administrator"));
    }

    @Test
    public void TestCreateUserNullOrEmptyOrWrongRole() {
        assertThrows(InvalidRoleException.class, ()-> ezShop.createUser("Mark","user",""));
        assertThrows(InvalidRoleException.class, ()-> ezShop.createUser("Luke","user",null));
        assertThrows(InvalidRoleException.class, ()-> ezShop.createUser("Andrew","user","owner"));
    }

}
