package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class TestDeleteProductFromSale {
    EZShop ezShop = new EZShop();

    @Test
    public void TestDeleteProductFromSaleCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        boolean res1 = ezShop.deleteProductFromSale(tid, "123456789104", 2);
        boolean res2 = ezShop.deleteProductFromSale(tid, "123456789104", 3);
        assertTrue(res1);
        assertTrue(res2);
    }

    @Test
    public void TestDeleteProductFromSaleTransactionUnauthorized() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.deleteProductFromSale(tid, "123456789104", 5));
    }

    @Test
    public void TestDeleteProductFromSaleTransactionQuantityException() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        assertThrows(InvalidQuantityException.class, ()-> ezShop.deleteProductFromSale(tid, "123456789104", -5));
    }

    @Test
    public void TestDeleteProductFromSaleTransactionInvalidCode() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.deleteProductFromSale(tid, null, 5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.deleteProductFromSale(tid,"",5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.deleteProductFromSale(tid,"ciao",5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.deleteProductFromSale(tid,"123456789103",5));
    }

    @Test
    public void TestDeleteProductNotInSaleTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
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
        ezShop.addProductToSale(tid,"123456789104",5);
        assertFalse(ezShop.deleteProductFromSale(tid, "2345678901234", 5));

    }

    @Test
    public void TestDeleteProductFromSaleTransactionInvalidTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.deleteProductFromSale(-1,"123456789104",5));
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.deleteProductFromSale(null,"123456789104",5));
    }


    @Test
    public void TestDeleteProductFromSaleTransactionProductNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        boolean res = ezShop.deleteProductFromSale(tid, "2345678901234", 2);
        assertFalse(res);
    }

    @Test
    public void TestDeleteProductFromSaleTransactionQuantityNotSufficient() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        boolean res = ezShop.deleteProductFromSale(tid, "123456789104", 10);
        assertFalse(res);
    }

    @Test
    public void TestDeleteProductFromSaleTransactionNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        boolean res = ezShop.deleteProductFromSale(tid+1, "123456789104", 2);
        assertFalse(res);
    }
}
