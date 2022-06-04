package it.polito.ezshop.changeRequestTest;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.SaleTransactionImpl;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class TestDeleteProductFromSaleRFID {
    EZShop ezShop = new EZShop();

    @Test
    public void TestDeleteProductFromSaleCorrectRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        boolean t1 = ezShop.addProductToSaleRFID(tId,"000000000001");
        boolean check = ezShop.deleteProductFromSaleRFID(tId,"000000000001");
        assertTrue(check);
        boolean t2  = ezShop.addProductToSaleRFID(tId,"000000000002");
        boolean t3  = ezShop.addProductToSaleRFID(tId,"000000000003");
        check = ezShop.deleteProductFromSaleRFID(tId,"000000000002");
        assertTrue(check);
        check = ezShop.deleteProductFromSaleRFID(tId,"000000000010");
        assertFalse(check);
        ezShop.endSaleTransaction(tId);
    }

    @Test
    public void TestDeleteProductFromSaleTransactionUnauthorizedRFID() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.deleteProductFromSaleRFID(tid, "000000000001"));
    }



    @Test
    public void TestDeleteProductFromSaleTransactionInvalidTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        boolean t1 = ezShop.addProductToSaleRFID(tId,"000000000001");
        boolean t2  = ezShop.addProductToSaleRFID(tId,"000000000002");
        boolean t3  = ezShop.addProductToSaleRFID(tId,"000000000003");
        boolean check = ezShop.deleteProductFromSaleRFID(tId,"000000000001");
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.deleteProductFromSaleRFID(-1,"00000000001"));
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.deleteProductFromSaleRFID(null,"00000000001"));
    }


    @Test
    public void TestDeleteProductFromSaleTransactionProductRFIDNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        boolean t1 = ezShop.addProductToSaleRFID(tId,"000000000001");
        boolean check = ezShop.deleteProductFromSaleRFID(tId,"000000000004");
        assertFalse(check);
        check = ezShop.deleteProductFromSaleRFID(tId,"000000000010");
        assertFalse(check);
    }


    @Test
    public void TestDeleteProductFromSaleTransactionNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        boolean t1 = ezShop.addProductToSaleRFID(tId,"000000000001");
        //NOT EXISTENT
        boolean res = ezShop.deleteProductFromSaleRFID(tId+1, "000000000001");
        assertFalse(res);
        ezShop.endSaleTransaction(tId);
        //CLOSED (NOT OPENED)
        res = ezShop.deleteProductFromSaleRFID(tId, "000000000001");
        assertFalse(res);
    }

    @Test
    public void TestDeleteProductFromSaleRFIDInvalidRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer tid = ezShop.startSaleTransaction();
        assertThrows(InvalidRFIDException.class,()->ezShop.deleteProductFromSaleRFID(tid,""));
        assertThrows(InvalidRFIDException.class,()->ezShop.deleteProductFromSaleRFID(tid,null));
        assertThrows(InvalidRFIDException.class,()->ezShop.deleteProductFromSaleRFID(tid,"0980043829"));
        assertThrows(InvalidRFIDException.class,()->ezShop.deleteProductFromSaleRFID(tid,"09f4003829a1"));
    }
}
