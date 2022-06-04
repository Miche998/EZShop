package it.polito.ezshop.changeRequestTest;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.SaleTransactionImpl;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class TestAddProductToSaleRFID {
    EZShop ezShop = new EZShop();

    @Test
    public void TestAddProductToSaleCorrectRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidRFIDException, InvalidOrderIdException {
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
        assertTrue(t1);
        boolean t2  = ezShop.addProductToSaleRFID(tId,"000000000002");
        assertTrue(t2);
        boolean t3  = ezShop.addProductToSaleRFID(tId,"000000000003");
        assertTrue(t3);

        boolean t4  = ezShop.addProductToSaleRFID(tId,"000000000001");
        assertFalse(t4);

        //NOT EXISTING RFID CASE
        boolean res = ezShop.addProductToSaleRFID(tId,"000000000010");
        assertFalse(res);

        boolean fake = ezShop.addProductToSaleRFID(10,"000000000004");
        assertFalse(fake);

        ezShop.endSaleTransaction(tId);
        boolean closed = ezShop.addProductToSaleRFID(tId,"000000000004");
        assertFalse(closed);
    }

    @Test
    public void TestAddProductToSaleTransactionUnauthorizedRFID() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.addProductToSaleRFID(tid,"000000000001"));
    }


    @Test
    public void TestAddProductToSaleTransactionInvalidTransactionRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tId,"123456789104",5);
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.addProductToSaleRFID(-1,"000000000001"));
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.addProductToSaleRFID(null,"000000000001"));
    }

    @Test
    public void TestAddProductToSaleTransactionNotExistentRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tId = ezShop.startSaleTransaction();
        boolean res = ezShop.addProductToSaleRFID(tId+1, "000000000001");
        assertFalse(res);
    }

    @Test
    public void TestAddProductToSaleRFIDInvalidRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer tid = ezShop.startSaleTransaction();
        assertThrows(InvalidRFIDException.class,()->ezShop.addProductToSaleRFID(tid,""));
        assertThrows(InvalidRFIDException.class,()->ezShop.addProductToSaleRFID(tid,null));
        assertThrows(InvalidRFIDException.class,()->ezShop.addProductToSaleRFID(tid,"0900843829"));
        assertThrows(InvalidRFIDException.class,()->ezShop.addProductToSaleRFID(tid,"09f4300829a1"));
    }

}
