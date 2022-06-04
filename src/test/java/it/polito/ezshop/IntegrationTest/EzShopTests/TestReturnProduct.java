package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestReturnProduct {
    EZShop ezShop = new EZShop();

    @Test
    public void TestReturnProductCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        boolean res = ezShop.returnProduct(rid,"123456789104",2);
        assertTrue(res);
        assertSame(45, ezShop.getProductTypeByBarCode("123456789104").getQuantity());
    }

    @Test
    public void TestReturnProductUnauthorized() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.returnProduct(rid,"123456789104",2));
    }

    @Test
    public void TestReturnProductQuantityException() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        assertThrows(InvalidQuantityException.class, ()-> ezShop.returnProduct(rid,"123456789104",-5));
    }

    @Test
    public void TestReturnProductInvalidCode() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.returnProduct(rid,null,5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.returnProduct(rid,"",5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.returnProduct(rid,"ciao",5));
        assertThrows(InvalidProductCodeException.class, ()-> ezShop.returnProduct(rid,"123456789103",5));
    }


    @Test
    public void TestReturnProductInvalidReturnId() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.returnProduct(-1,"123456789104",2));
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.returnProduct(null,"123456789104",2));
    }

    @Test
    public void TestReturnProductTransactionNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        boolean res = ezShop.returnProduct(rid+1,"123456789104",2);
        assertFalse(res);
    }

    @Test
    public void TestReturnTooMuchProduct() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        boolean res = ezShop.returnProduct(rid,"123456789104",7);
        assertFalse(res);
    }

    @Test
    public void TestReturnProductNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        boolean res = ezShop.returnProduct(rid,"2345678901234",2);
        assertFalse(res);
    }

    @Test
    public void TestReturnProductNotInTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
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
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        boolean res = ezShop.returnProduct(rid,"2345678901234",2);
        assertFalse(res);
    }
}
