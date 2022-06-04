package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestReturnCreditCardPayment {
    EZShop ezShop = new EZShop();
    @Test
    public void TestReturnCreditCardPayment() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidCreditCardException {
        ezShop.reset();
        ezShop.createUser("user", "user", "Administrator");
        ezShop.login("user", "user");

        Integer pId = ezShop.createProductType("Shoes", "123456789104", 15.0, "note");
        ezShop.updatePosition(pId, "1-A-1");
        ezShop.updateQuantity(pId, 10);

        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 2);
        ezShop.endSaleTransaction(tid);

        //sale exists enough money
        double change = ezShop.receiveCashPayment(tid, 50.0);

        double balance = ezShop.computeBalance();

        Integer rId = ezShop.startReturnTransaction(tid);
        ezShop.returnProduct(rId,"123456789104",2);
        //not ended return transaction
        double money = ezShop.returnCreditCardPayment(rId,"4716258050958645");
        assertEquals(-1.0,money,0.0);
        ezShop.endReturnTransaction(rId,true);

        //nominal case
        money = ezShop.returnCreditCardPayment(rId,"4716258050958645");
        assertEquals(30,money,0.0);
        assertEquals((balance-30),ezShop.computeBalance(),0.0);

        //not existing return transaction
        money = ezShop.returnCreditCardPayment(10,"4716258050958645");
        assertEquals(-1.0,money,0.0);

        //not existing Credit Card
        money = ezShop.returnCreditCardPayment(rId,"123456789429");
        assertEquals(-1.0,money,0.0);
    }

    @Test
    public void TestReturnCreditCardPaymentInvalidTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user", "user", "Administrator");
        ezShop.login("user", "user");
        assertThrows(InvalidTransactionIdException.class,()-> ezShop.returnCreditCardPayment(-1,"4716258050958645"));
        assertThrows(InvalidTransactionIdException.class,()-> ezShop.returnCreditCardPayment(null,"4716258050958645"));
    }

    @Test
    public void TestReturnCreditCardPaymentUnauthorized() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        Integer pId = ezShop.createProductType("Shoes","123456789104",15.0,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.updateQuantity(pId,10);

        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",2);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,50);
        Integer rId = ezShop.startReturnTransaction(tid);
        ezShop.returnProduct(rId,"123456789104",1);
        ezShop.logout();
        assertThrows(UnauthorizedException.class,()-> ezShop.returnCreditCardPayment(rId,"4716258050958645"));
    }

    @Test
    public void TestReceiveCreditCardPaymentInvalidCreditCard() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        Integer pId = ezShop.createProductType("Shoes","123456789104",15.0,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.updateQuantity(pId,10);

        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",2);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,50);
        Integer rId = ezShop.startReturnTransaction(tid);
        ezShop.returnProduct(rId,"123456789104",1);
        assertThrows(InvalidCreditCardException.class,()-> ezShop.returnCreditCardPayment(rId,""));
        assertThrows(InvalidCreditCardException.class,()-> ezShop.returnCreditCardPayment(rId,null));
        assertThrows(InvalidCreditCardException.class,()-> ezShop.returnCreditCardPayment(rId,"A2432A432432"));
        ezShop.logout();

    }

}
