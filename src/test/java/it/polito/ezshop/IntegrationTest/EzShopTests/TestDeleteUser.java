package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDeleteUser {
    EZShop ezShop = new EZShop();
    @Test
    public void TestDeleteUserCorrect() throws InvalidUserIdException, UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Uid = ezShop.createUser("John","user","Cashier");
        assertSame(2,Uid);
        boolean check = ezShop.deleteUser(Uid);
        assertTrue(check);
        assertNull(ezShop.getUser(Uid));
    }

    @Test
    public void TestDeleteUserWrong() throws InvalidUserIdException, UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Uid = ezShop.createUser("John","user","Cashier");
        boolean check = ezShop.deleteUser(3);
        assertFalse(check);
    }
    @Test
    public void TestDeleteUserIdNull(){
        assertThrows(InvalidUserIdException.class,()->ezShop.deleteUser(null));
        assertThrows(InvalidUserIdException.class,()->ezShop.deleteUser(-1));
    }
    @Test
    public void TestDeleteUserWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Cashier");
        Integer cId = ezShop.createUser("Mark","user","Cashier");
        assertThrows(UnauthorizedException.class,()->ezShop.deleteUser(cId));
        ezShop.login("user","user");
        assertThrows(UnauthorizedException.class,()->ezShop.deleteUser(cId));
    }

}
