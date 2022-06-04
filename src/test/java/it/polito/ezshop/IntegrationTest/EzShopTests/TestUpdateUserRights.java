package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUpdateUserRights {
    EZShop ezShop = new EZShop();
    @Test
    public void TestUpdateUserRightsCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        Integer uId = ezShop.createUser("user","user","Administrator");
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        boolean check = ezShop.updateUserRights(uId,"Cashier");
        assertTrue(check);
        assertEquals("Cashier",ezShop.getUser(uId).getRole());
    }

    @Test
    public void TestUpdateUserRightsWrong() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        Integer uId = ezShop.createUser("user","user","Administrator");
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        boolean check = ezShop.updateUserRights(3,"Cashier");
        assertFalse(check);

    }
    @Test
    public void TestUpdateUserRightsNullOrNegativeId() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        Integer uId = ezShop.createUser("user","user","Administrator");
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        assertThrows(InvalidUserIdException.class, ()->ezShop.updateUserRights(null,"Cashier"));
        assertThrows(InvalidUserIdException.class, ()->ezShop.updateUserRights(-1,"Cashier"));
    }

    @Test
    public void TestUpdateUserRightsNullOrEmptyRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        Integer uId = ezShop.createUser("user","user","Administrator");
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        assertThrows(InvalidRoleException.class, ()->ezShop.updateUserRights(uId,""));
        assertThrows(InvalidRoleException.class, ()->ezShop.updateUserRights(uId,null));
        assertThrows(InvalidRoleException.class, ()->ezShop.updateUserRights(uId,"cashier"));
    }

    @Test
    public void TestUpdateUserRightsWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidUserIdException, UnauthorizedException {
        ezShop.reset();
        Integer uId = ezShop.createUser("user","user","Administrator");
        ezShop.createUser("Luke","user","Administrator");
        assertThrows(UnauthorizedException.class, ()->ezShop.updateUserRights(uId,"Cashier"));
        assertThrows(UnauthorizedException.class, ()->ezShop.updateUserRights(uId,"Cashier"));
        ezShop.createUser("Mark","user","Cashier");
        ezShop.login("Mark","user");
        assertThrows(UnauthorizedException.class, ()->ezShop.updateUserRights(uId,"Cashier"));

    }
}
