package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestComputeBalance {
    EZShop ezShop = new EZShop();

    @Test
    public void TestComputeBalance() throws UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user", "user", "Administrator");
        ezShop.login("user", "user");

        ezShop.recordBalanceUpdate(100);
        ezShop.recordBalanceUpdate(-50);
        ezShop.recordBalanceUpdate(120);
        assertEquals(170.0,ezShop.computeBalance(),0.0);
    }

    @Test
    public void TestComputeBalanceUnauthorizedException() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user", "user", "Cashier");
        assertThrows(UnauthorizedException.class,()->ezShop.computeBalance());
        ezShop.login("user", "user");
        assertThrows(UnauthorizedException.class,()->ezShop.computeBalance());
    }
}
