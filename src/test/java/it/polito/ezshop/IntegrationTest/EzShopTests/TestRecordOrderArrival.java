package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRecordOrderArrival {
    EZShop ezShop = new EZShop();

    @Test
    public void TestRecordOrderArrivalNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        Integer pid = ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        ezShop.updatePosition(pid,"1-A-1");
        Integer oId = ezShop.payOrderFor("1234567890111",5,2.5);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.recordOrderArrival(oId));
        ezShop.createUser("user1","user1","Cashier");
        ezShop.login("user1","user1");
        assertThrows(UnauthorizedException.class, ()-> ezShop.recordOrderArrival(oId));
    }

    @Test
    public void TestRecordOrderArrivalInvalidOrderId() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidQuantityException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        Integer pid = ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        ezShop.updatePosition(pid,"1-A-1");
        ezShop.payOrderFor("1234567890111",5,2.5);

        assertThrows(InvalidOrderIdException.class, ()->ezShop.recordOrderArrival(0));
        assertThrows(InvalidOrderIdException.class, ()->ezShop.recordOrderArrival(-1));
        assertThrows(InvalidOrderIdException.class, ()-> ezShop.recordOrderArrival(null));
    }

    @Test
    public void TestRecordOrderArrivalNoLocation() throws InvalidPasswordException, InvalidUsernameException, InvalidRoleException, InvalidQuantityException, UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductDescriptionException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);
        ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        Integer oId = ezShop.payOrderFor("1234567890111",5,2.5);

        assertThrows(InvalidLocationException.class, ()-> ezShop.recordOrderArrival(oId));

    }

    @Test
    public void TestRecordOrderArrivalCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidLocationException, UnauthorizedException, InvalidOrderIdException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException, InvalidQuantityException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.recordBalanceUpdate(500.00);

        //Order not previously ISSUED
        Integer pId = ezShop.createProductType("shoes","1234567890111", 2.5, "Comfortable");
        ezShop.updatePosition(pId,"1-A-1");
        Integer oId = ezShop.issueOrder("1234567890111",15,2.5);

        assertFalse(ezShop.recordOrderArrival(oId));

        ezShop.payOrder(oId);

        assertTrue(ezShop.recordOrderArrival(oId));
        assertSame(15, ezShop.getProductTypeByBarCode("1234567890111").getQuantity());

        assertFalse(ezShop.recordOrderArrival(oId));

        //Order not existing
        assertFalse(ezShop.recordOrderArrival(oId+1));

    }
}
