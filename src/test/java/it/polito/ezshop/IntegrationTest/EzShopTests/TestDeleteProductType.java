package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDeleteProductType {
    EZShop ezShop = new EZShop();

    @Test
    public void TestDeleteProductTypeCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        assertTrue(ezShop.deleteProductType(pId));
        assertNull(ezShop.getProductTypeByBarCode("0123456789104"));
    }

    @Test
    public void TestDeleteProductTypeInvalidId() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        assertThrows(InvalidProductIdException.class,()->ezShop.deleteProductType(-1));
        assertThrows(InvalidProductIdException.class,()->ezShop.deleteProductType(null));
    }

    @Test
    public void TestDeleteProductTypeNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        assertThrows(UnauthorizedException.class,()->ezShop.deleteProductType(pId));
    }

    @Test
    public void TestDeleteProductTypeWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        ezShop.createUser("user2","user2","Cashier");
        ezShop.login("user2","user2");
        assertThrows(UnauthorizedException.class,()->ezShop.deleteProductType(pId));
    }

}
