package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPayOrder {
    EZShop ezShop = new EZShop();

    @Test
    public void TestPayOrderNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        Integer oId = ezShop.issueOrder("1234567890111",5,2.5);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.payOrder(oId));
        ezShop.createUser("user1","user1","Cashier");
        ezShop.login("user1","user1");
        assertThrows(UnauthorizedException.class, ()-> ezShop.payOrder(oId));
    }

    @Test
    public void TestPayOrderInvalidOrderId() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        ezShop.issueOrder("1234567890111",5,2.5);
        assertThrows(InvalidOrderIdException.class, ()->ezShop.payOrder(0));
        assertThrows(InvalidOrderIdException.class, ()->ezShop.payOrder(-1));
        assertThrows(InvalidOrderIdException.class, ()-> ezShop.payOrder(null));
    }

    @Test
    public void TestPayOrderCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidOrderIdException, InvalidQuantityException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductDescriptionException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        //Order not previously ISSUED
        Integer pId = ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        ezShop.updatePosition(pId,"1-A-1");
        Integer oId = ezShop.issueOrder("1234567890111",4,2.5);
        assertFalse(ezShop.payOrder(oId));
        ezShop.recordBalanceUpdate(500.00);
        assertTrue(ezShop.payOrder(oId));
        assertEquals(490.0, ezShop.computeBalance(), 0);
        assertFalse(ezShop.payOrder(oId));
        ezShop.recordOrderArrival(oId);
        assertFalse(ezShop.payOrder(oId));

        //Order not existing
        assertFalse(ezShop.payOrder(oId+1));

    }
}
