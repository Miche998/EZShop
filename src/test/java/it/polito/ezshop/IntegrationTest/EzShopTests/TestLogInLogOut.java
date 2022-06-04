package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestLogInLogOut {
    EZShop ezShop = new EZShop();
    @Test
    public void TestLogInCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        User u = ezShop.login("Luke","user");
        assertEquals("Luke",u.getUsername());
        assertEquals("user",u.getPassword());
        assertEquals("Administrator",u.getRole());
    }

    @Test
    public void TestLogInWrong() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        User u = ezShop.login("Luke","luke");
        assertNull(u);
    }

    @Test
    public void TestLogInWrongUsername() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        assertThrows(InvalidUsernameException.class,()->ezShop.login(null,"user"));
        assertThrows(InvalidUsernameException.class,()->ezShop.login("","user"));
    }

    @Test
    public void TestLogInWrongPassword() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        assertThrows(InvalidPasswordException.class,()->ezShop.login("Luke",""));
        assertThrows(InvalidPasswordException.class,()->ezShop.login("Luke",null));
    }

    @Test
    public void TestLogOutCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        boolean check = ezShop.logout();
        assertTrue(check);
    }

    @Test
    public void TestLogOutWrong() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        boolean check = ezShop.logout();
        assertFalse(check);
    }
}
