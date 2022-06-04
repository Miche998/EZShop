package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPayOrderFor {
    EZShop ezShop = new EZShop();

    @Test
    public void TestPayOrderForNoLoggedUser() throws UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.payOrderFor("1234567890111",5,2.5));
        ezShop.createUser("user1","user1","Cashier");
        ezShop.login("user1","user1");
        assertThrows(UnauthorizedException.class, ()-> ezShop.payOrderFor("1234567890111",6,4.5));
    }

    @Test
    public void TestPayOrderForPricePerUnitNegativeOrZero() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidPricePerUnitException.class, ()-> ezShop.payOrderFor("1234567890111",5, -3.5));
        assertThrows(InvalidPricePerUnitException.class, ()-> ezShop.payOrderFor("1234567890111",5, 0));
    }

    @Test
    public void TestPayOrderForQuantityNegativeOrZero() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidQuantityException.class, ()-> ezShop.payOrderFor("1234567890111",-2, 5.5));
        assertThrows(InvalidQuantityException.class, ()-> ezShop.payOrderFor("1234567890111",0, 5.5));
    }

    @Test
    public void TestPayOrderForProductCodeNullOrEmpty() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder("", 1, 2.5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder(null, 1, 2.5));
    }

    @Test
    public void TestPayOrderForProductCodeBadLength() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder("12345678922", 1, 2.5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder("123456789212356", 1, 2.5));
    }

    @Test
    public void TestPayOrderForInvalidProductCode() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder("123456789013", 1, 2.5));
    }

    @Test
    public void TestPayOrderForCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidProductIdException, InvalidLocationException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");

        //Making an order with a non existing product
        Integer oId = ezShop.payOrderFor("1234567890128",6,3.6);
        assertSame(-1, oId);

        //Paying an order with no money on the balance
        assertSame(-1, ezShop.payOrderFor("1234567890111",6,3.6));

        //Correct payment for an order (checks for order ID returned)
        ezShop.recordBalanceUpdate(500.00);
        Integer pOid = ezShop.payOrderFor("1234567890111",4,2.5);
        assertSame(1, pOid);
        assertEquals(490.00, ezShop.computeBalance(), 0);

    }
}
