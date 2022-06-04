package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

public class TestStartSaleTransaction {
    EZShop ezShop = new EZShop();

    @Test
    public void TestStartSaleTransactionCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer tid = ezShop.startSaleTransaction();
        assertSame(1,tid);
    }

    @Test
    public void TestStartSaleTransactionUnauthorized() {
        ezShop.reset();
        assertThrows(UnauthorizedException.class, ()-> ezShop.startSaleTransaction());
    }
}
