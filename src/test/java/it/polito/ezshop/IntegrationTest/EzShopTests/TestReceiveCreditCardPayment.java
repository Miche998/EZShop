package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestReceiveCreditCardPayment {
    EZShop ezShop = new EZShop();

    @Test
    public void TestReceiveCreditCardPayment() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidCreditCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        Integer pId = ezShop.createProductType("Shoes","123456789104",15.0,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.updateQuantity(pId,10);

        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",2);
        ezShop.endSaleTransaction(tid);

        //sale exists enough money
        boolean check = ezShop.receiveCreditCardPayment(tid,"4716258050958645");
        assertTrue(check);
        //sale does not exist
        check = ezShop.receiveCreditCardPayment(5,"4716258050958645");
        assertFalse(check);
        //sale exists but card not registered
        Integer tid2 = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid2,"123456789104",2);
        ezShop.endSaleTransaction(tid2);
        check = ezShop.receiveCreditCardPayment(tid2,"123456789429");
        assertFalse(check);
        //sale exists but card not enough money
        check = ezShop.receiveCreditCardPayment(tid2,"5100293991053009");
        assertFalse(check);
    }

    @Test
    public void TestReceiveCreditCardPaymentInvalidTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidTransactionIdException.class,()-> ezShop.receiveCreditCardPayment(-1,"4716258050958645"));
        assertThrows(InvalidTransactionIdException.class,()-> ezShop.receiveCreditCardPayment(null,"4716258050958645"));
    }

    @Test
    public void TestReceiveCreditCardPaymentUnauthorized() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
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
        assertThrows(UnauthorizedException.class,()-> ezShop.receiveCreditCardPayment(tid,"4716258050958645"));
    }

    @Test
    public void TestReceiveCreditCardPaymentInvalidCreditCard() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        Integer pId = ezShop.createProductType("Shoes","123456789104",15.0,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.updateQuantity(pId,10);

        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",2);
        ezShop.endSaleTransaction(tid);
        assertThrows(InvalidCreditCardException.class,()-> ezShop.receiveCreditCardPayment(tid,""));
        assertThrows(InvalidCreditCardException.class,()-> ezShop.receiveCreditCardPayment(tid,null));
        assertThrows(InvalidCreditCardException.class,()-> ezShop.receiveCreditCardPayment(tid,"432432432432"));
        ezShop.logout();

    }
}
