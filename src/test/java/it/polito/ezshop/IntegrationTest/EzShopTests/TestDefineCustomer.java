package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDefineCustomer {
    EZShop ezShop = new EZShop();

    @Test
    public void TestDefineCustomerCorrect() throws InvalidCustomerNameException, UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        assertSame(1,Cid);
    }

    @Test
    public void TestDefineCustomerExistingCustomer() throws InvalidCustomerNameException, UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid1 = ezShop.defineCustomer("Zlatan");
        Integer Cid2 = ezShop.defineCustomer("Zlatan");
        assertSame(1,Cid1);
        assertSame(-1,Cid2);
    }

    @Test
    public void TestDefineCustomerNullOrEmptyName() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidCustomerNameException.class, ()-> ezShop.defineCustomer(""));
        assertThrows(InvalidCustomerNameException.class, ()-> ezShop.defineCustomer(null));
    }

    @Test
    public void TestDefineCustomerNoUserLogged(){
        ezShop.reset();
        assertThrows(UnauthorizedException.class, ()-> ezShop.defineCustomer("Zlatan"));
    }
}
