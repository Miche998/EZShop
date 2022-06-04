package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDeleteCustomer {
    EZShop ezShop = new EZShop();

    @Test
    public void TestDeleteCustomerCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        boolean check = ezShop.deleteCustomer(Cid);
        assertTrue(check);
    }

    @Test
    public void TestDeleteCustomerIdNullOrNegative() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.deleteCustomer(null));
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.deleteCustomer(-5));
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.deleteCustomer(0));
    }

    @Test
    public void TestDeleteCustomerNoUserLogged() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.deleteCustomer(Cid));
    }

    @Test
    public void TestDeleteCustomerNonExistingCustomer() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerIdException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        boolean check = ezShop.deleteCustomer(1);
        assertFalse(check);
    }
}
