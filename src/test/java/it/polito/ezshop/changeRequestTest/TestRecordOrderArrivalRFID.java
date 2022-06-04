package it.polito.ezshop.changeRequestTest;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class TestRecordOrderArrivalRFID {
    EZShop ezShop = new EZShop();

    @Test
    public void TestRecordOrderArrivalNoLoggedUserRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        Integer pid = ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        ezShop.updatePosition(pid,"1-A-1");
        Integer oId = ezShop.payOrderFor("1234567890111",5,2.5);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.recordOrderArrivalRFID(oId,"0000000001"));
        ezShop.createUser("user1","user1","Cashier");
        ezShop.login("user1","user1");
        assertThrows(UnauthorizedException.class, ()-> ezShop.recordOrderArrivalRFID(oId,"0000000001"));
    }

    @Test
    public void TestRecordOrderArrivalInvalidOrderIdRFID() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);

        assertThrows(InvalidOrderIdException.class, ()->ezShop.recordOrderArrivalRFID(0,"0000000001"));
        assertThrows(InvalidOrderIdException.class, ()->ezShop.recordOrderArrivalRFID(-1,"0000000001"));
        assertThrows(InvalidOrderIdException.class, ()-> ezShop.recordOrderArrivalRFID(null,"0000000001"));
    }

    @Test
    public void TestRecordOrderArrivalNoLocationRFID() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, InvalidQuantityException, UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductDescriptionException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        assertThrows(InvalidLocationException.class, ()-> ezShop.recordOrderArrivalRFID(oId,"00000000001"));
    }

    @Test
    public void TestRecordOrderArrivalCorrectRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidLocationException, UnauthorizedException, InvalidOrderIdException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidQuantityException, InvalidRFIDException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer p = ezShop.createProductType("p1","123456789104", 15.00, "Note");
        ezShop.updatePosition(p,"1-A-1");
        ezShop.recordBalanceUpdate(1000);
        Integer oId = ezShop.payOrderFor("123456789104",5,10);
        boolean check = ezShop.recordOrderArrivalRFID(oId,"000000000001");
        assertTrue(check);

        //ISSUED ORDER
        Integer oId2 = ezShop.issueOrder("123456789104",2,8.00);
        check = ezShop.recordOrderArrivalRFID(oId2,"000000000006");
        assertFalse(check);

        //NOT EXISTING ORDER
        check = ezShop.recordOrderArrivalRFID(oId+2,"000000000009");
        assertFalse(check);

    }

    @Test
    public void TestRecordOrderArrivalRFIDInvalidRFID() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidRFIDException, InvalidOrderIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        Integer pid = ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        ezShop.updatePosition(pid,"1-A-1");
        Integer oId = ezShop.payOrderFor("1234567890111",5,2.5);
        ezShop.recordOrderArrivalRFID(oId,"000000000001");
        Integer oId2 = ezShop.payOrderFor("1234567890111",5,2.5);
        assertThrows(InvalidRFIDException.class,()->ezShop.recordOrderArrivalRFID(oId,""));
        assertThrows(InvalidRFIDException.class,()->ezShop.recordOrderArrivalRFID(oId,null));
        assertThrows(InvalidRFIDException.class,()->ezShop.recordOrderArrivalRFID(oId,"0984003829"));
        assertThrows(InvalidRFIDException.class,()->ezShop.recordOrderArrivalRFID(oId,"09f4003829a1"));
        assertThrows(InvalidRFIDException.class,()->ezShop.recordOrderArrivalRFID(oId2,"000000000001"));
    }
}
