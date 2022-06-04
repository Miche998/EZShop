package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRecordBalanceUpdate {

    EZShop ezShop = new EZShop();
    @Test
    public void TestRecordBalanceUpdate() throws UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        boolean check = ezShop.recordBalanceUpdate(100);
        assertTrue(check);

        check = ezShop.recordBalanceUpdate(-50);
        assertTrue(check);

        check=ezShop.recordBalanceUpdate(-100);
        assertFalse(check);
    }

    @Test
    public void TestRecordBalanceUpdateUnauthorizedUser() throws UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user", "user", "Cashier");
        assertThrows(UnauthorizedException.class, () -> ezShop.recordBalanceUpdate(20));
        ezShop.login("user", "user");
        assertThrows(UnauthorizedException.class, () -> ezShop.recordBalanceUpdate(20));
    }
}
