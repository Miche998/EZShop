package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class TestApplyDiscountRateToProduct {
    EZShop ezShop = new EZShop();

    @Test
    public void TestApplyDiscountRateToProductCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        boolean res = ezShop.applyDiscountRateToProduct(tid,"123456789104",0.5);
        assertTrue(res);
    }

    @Test
    public void TestApplyDiscountRateToProductUnauthorized() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.applyDiscountRateToProduct(tid,"123456789104",0.5));
    }

    @Test
    public void TestApplyInvalidDiscountRateToProduct() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        assertThrows(InvalidDiscountRateException.class, ()-> ezShop.applyDiscountRateToProduct(tid,"123456789104",-0.5));
    }

    @Test
    public void TestApplyDiscountRateToProductInvalidCode() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.applyDiscountRateToProduct(tid, null, 0.5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.applyDiscountRateToProduct(tid,"",0.5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.applyDiscountRateToProduct(tid,"ciao",0.5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.applyDiscountRateToProduct(tid,"123456789103",0.5));
    }


    @Test
    public void TestApplyDiscountRateToProductInvalidTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.applyDiscountRateToProduct(-1,"123456789104",0.5));
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.applyDiscountRateToProduct(null,"123456789104",0.5));
    }


    @Test
    public void TestApplyDiscountRateToProductNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        boolean res = ezShop.applyDiscountRateToProduct(tid, "2345678901234", 0.2);
        assertFalse(res);
    }

    @Test
    public void TestApplyDiscountRateToProductNotInTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        pid = ezShop.createProductType("Description2", "2345678901234", 10.00, "Note2");
        ezShop.updatePosition(pid, "1-B-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        boolean res = ezShop.applyDiscountRateToProduct(tid, "2345678901234", 0.2);
        assertFalse(res);
    }


    @Test
    public void TestApplyDiscountRateToProductTransactionNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        boolean res = ezShop.applyDiscountRateToProduct(tid+1, "123456789104", 0.2);
        assertFalse(res);
    }
}
