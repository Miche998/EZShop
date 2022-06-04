package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class TestApplyDiscountRateToSale {
    EZShop ezShop = new EZShop();

    @Test
    public void TestApplyDiscountRateToSaleCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        boolean res = ezShop.applyDiscountRateToSale(tid, 0.5);
        assertTrue(res);
    }

    @Test
    public void TestApplyDiscountRateToSaleUnauthorized() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.applyDiscountRateToSale(tid, 0.5));
    }

    @Test
    public void TestApplyInvalidDiscountRateToSale() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        assertThrows(InvalidDiscountRateException.class, ()-> ezShop.applyDiscountRateToSale(tid,-0.5));
    }


    @Test
    public void TestApplyDiscountRateToSaleInvalidTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.applyDiscountRateToSale(-1,0.5));
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.applyDiscountRateToSale(null,0.5));
    }


    @Test
    public void TestDeleteProductFromSaleTransactionNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        boolean res = ezShop.applyDiscountRateToSale(tid+1, 0.2);
        assertFalse(res);
    }
}
