package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.User;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestReset {
    EZShop ezShop = new EZShop();

    @Test
    public void TestReset() throws UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidTransactionIdException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 10.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.endSaleTransaction(tid);
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        assertEquals(0,ezShop.computeBalance(),0);
        assertEquals(0, ezShop.getAllProductTypes().toArray().length);
        assertNull(ezShop.getSaleTransaction(tid));
    }
}
