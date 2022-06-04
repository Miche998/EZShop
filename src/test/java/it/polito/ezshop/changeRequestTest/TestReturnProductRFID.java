package it.polito.ezshop.changeRequestTest;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class TestReturnProductRFID {
    EZShop ezShop = new EZShop();

    @Test
    public void TestReturnProductCorrectRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException, InvalidRFIDException, InvalidOrderIdException, InvalidCreditCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        ezShop.addProductToSaleRFID(tId,"000000000001");
        ezShop.addProductToSaleRFID(tId,"000000000002");
        ezShop.addProductToSaleRFID(tId,"000000000003");
        ezShop.endSaleTransaction(tId);
        ezShop.receiveCreditCardPayment(tId,"4485370086510891");
        Integer rid = ezShop.startReturnTransaction(tId);

        boolean check = ezShop.returnProductRFID(rid,"000000000001");

        //TO BE CHECKED SALE TRANSACTION DELETES THE RFID MAP AFTER ENDING

        assertTrue(check);

        check = ezShop.returnProductRFID(rid,"000000000001");
        assertFalse(check);

        //NOT EXISTING PRODUCT
        check = ezShop.returnProductRFID(rid,"000000000201");
        assertFalse(check);
        //EXISTING BUT NOT IN TRANSACTION
        check = ezShop.returnProductRFID(rid,"000000000004");
        assertFalse(check);
        //NOT EXISTING TRANSACTION
        check = ezShop.returnProductRFID(rid+1,"000000000002");
        assertFalse(check);
        ezShop.endReturnTransaction(rid,true);
        ezShop.returnCreditCardPayment(rid,"4485370086510891");



    }

    @Test
    public void TestReturnProductUnauthorizedRFID() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidPaymentException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        ezShop.addProductToSaleRFID(tId,"000000000001");
        ezShop.addProductToSaleRFID(tId,"000000000002");
        ezShop.addProductToSaleRFID(tId,"000000000003");
        ezShop.endSaleTransaction(tId);
        ezShop.receiveCashPayment(tId,100);
        Integer rid = ezShop.startReturnTransaction(tId);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.returnProductRFID(rid,"000000000001"));
    }



    @Test
    public void TestReturnProductInvalidReturnIdRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        ezShop.addProductToSaleRFID(tId,"000000000001");
        ezShop.addProductToSaleRFID(tId,"000000000002");
        ezShop.addProductToSaleRFID(tId,"000000000003");
        ezShop.endSaleTransaction(tId);
        ezShop.receiveCashPayment(tId,100);
        Integer rid = ezShop.startReturnTransaction(tId);
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.returnProductRFID(-1,"000000000001"));
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.returnProductRFID(null,"000000000001"));
    }



    @Test
    public void TestReturnProductRFIDInvalidRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        ezShop.addProductToSaleRFID(tId,"000000000001");
        ezShop.addProductToSaleRFID(tId,"000000000002");
        ezShop.addProductToSaleRFID(tId,"000000000003");
        ezShop.endSaleTransaction(tId);
        ezShop.receiveCashPayment(tId,100);
        Integer rid = ezShop.startReturnTransaction(tId);
        assertThrows(InvalidRFIDException.class, ()-> ezShop.returnProductRFID(rid,"0000000001"));
        assertThrows(InvalidRFIDException.class, ()-> ezShop.returnProductRFID(rid,""));
        assertThrows(InvalidRFIDException.class, ()-> ezShop.returnProductRFID(rid,null));
        assertThrows(InvalidRFIDException.class, ()-> ezShop.returnProductRFID(rid,"01a2d00g46j8"));
    }
}
