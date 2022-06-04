package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestIssueOrder {
    EZShop ezShop = new EZShop();

    @Test
    public void TestIssueOrderNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.issueOrder("1234567890111",5,2.5));
        ezShop.createUser("user1","user1","Cashier");
        ezShop.login("user1","user1");
        assertThrows(UnauthorizedException.class, ()-> ezShop.issueOrder("1234567890111",6,4.5));
    }

    @Test
    public void TestIssueOrderPricePerUnitNegativeOrZero() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidPricePerUnitException.class, ()-> ezShop.issueOrder("1234567890111",5, -3.5));
        assertThrows(InvalidPricePerUnitException.class, ()-> ezShop.issueOrder("1234567890111",5, 0));
    }

    @Test
    public void TestIssueOrderQuantityNegativeOrZero() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidQuantityException.class, ()-> ezShop.issueOrder("1234567890111",-2, 5.5));
        assertThrows(InvalidQuantityException.class, ()-> ezShop.issueOrder("1234567890111",0, 5.5));
    }

    @Test
    public void TestIssueOrderProductCodeNullOrEmpty() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder("", 1, 2.5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder(null, 1, 2.5));
    }

    @Test
    public void TestIssueOrderProductCodeBadLength() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder("12345678922", 1, 2.5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder("123456789212356", 1, 2.5));
    }

    @Test
    public void TestIssueOrderInvalidProductCode() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.issueOrder("123456789013", 1, 2.5));
    }

    @Test
    public void TestIssueOrderCorrect() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidPasswordException, InvalidUsernameException, InvalidRoleException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        Integer oId = ezShop.issueOrder("1234567890128",6,3.6);
        assertSame(-1, oId);

        Integer oId2 = ezShop.issueOrder("1234567890111",6,3.6);
        assertSame(1,oId2);

    }

}
