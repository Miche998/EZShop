package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestReturnCashPayment {
    EZShop ezShop = new EZShop();
    @Test
    public void TestReturnCashPayment() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
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
        ezShop.returnProduct(rId,"123456789104",1);
        //not endend return transaction
        double money = ezShop.returnCashPayment(rId);
        assertEquals(-1.0,money,0.0);
        ezShop.endReturnTransaction(rId,true);

        //nominal case
        money = ezShop.returnCashPayment(rId);
        assertEquals(15.0,money,0.0);
        assertEquals((balance-15.0),ezShop.computeBalance(),0.0);

        //not existing return transaction
        money = ezShop.returnCashPayment(10);
        assertEquals(-1.0,money,0.0);
    }

    @Test
    public void TestReturnCashPaymentInvalidTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user", "user", "Administrator");
        ezShop.login("user", "user");
        assertThrows(InvalidTransactionIdException.class,()-> ezShop.returnCashPayment(-1));
        assertThrows(InvalidTransactionIdException.class,()-> ezShop.returnCashPayment(null));
    }

    @Test
    public void TestReturnCashPaymentUnauthorized() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
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
        assertThrows(UnauthorizedException.class,()-> ezShop.returnCashPayment(rId));
    }
}
