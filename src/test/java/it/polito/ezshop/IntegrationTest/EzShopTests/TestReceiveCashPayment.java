package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestReceiveCashPayment {
    EZShop ezShop = new EZShop();

    @Test
    public void TestReceiveCashPayment() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        Integer pId = ezShop.createProductType("Shoes","123456789104",15.0,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.updateQuantity(pId,10);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",2);
        double balance = ezShop.computeBalance();
        ezShop.endSaleTransaction(tid);

        //sale exists enough money
        double change = ezShop.receiveCashPayment(tid,50.0);
        assertEquals(20.0,change,0.0);
        assertEquals((balance+50.0-20.0),ezShop.computeBalance(),0.0);

        //sale does not exist
        change = ezShop.receiveCashPayment(5,50.0);
        assertEquals(-1,change,0);
        //sale exists but not enough money
        Integer tid2 = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid2,"123456789104",2);
        ezShop.endSaleTransaction(tid2);
        change = ezShop.receiveCashPayment(tid2,20.0);
        assertEquals(-1,change,0);
    }

    @Test
    public void TestReceiveCashPaymentInvalidTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidTransactionIdException.class,()-> ezShop.receiveCashPayment(-1,50.0));
        assertThrows(InvalidTransactionIdException.class,()-> ezShop.receiveCashPayment(null,50.0));
    }

    @Test
    public void TestReceiveCashPaymentInvalidPayment() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("Shoes","123456789104",15.0,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.updateQuantity(pId,10);

        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",2);
        ezShop.endSaleTransaction(tid);

        assertThrows(InvalidPaymentException.class,()-> ezShop.receiveCashPayment(tid,-50.0));
    }

    @Test
    public void TestReceiveCashPaymentUnauthorized() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        Integer pId = ezShop.createProductType("Shoes","123456789104",15.0,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.updateQuantity(pId,10);

        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",2);
        ezShop.endSaleTransaction(tid);
        ezShop.logout();
        assertThrows(UnauthorizedException.class,()-> ezShop.receiveCashPayment(tid,50.0));
    }
}
