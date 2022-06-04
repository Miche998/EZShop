package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;

public class TestUpdateQuantity {

    EZShop ezShop = new EZShop();

    @Test
    public void TestUpdateQuantityCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.updatePosition(pId,"1-A-1");
        int act_qnt = ezShop.getProductTypeByBarCode("0123456789104").getQuantity();
        int tobeAdded = 10;
        boolean check = ezShop.updateQuantity(pId,tobeAdded);
        int final_qnt = ezShop.getProductTypeByBarCode("0123456789104").getQuantity();
        assertEquals((act_qnt+tobeAdded),final_qnt);
        assertTrue(check);
    }

    @Test
    public void TestUpdateQuantityProductNotExists() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        boolean check = ezShop.updateQuantity(2,10);
        assertFalse(check);
    }

    @Test
    public void TestUpdateQuantityNegativeQuantity() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.getProductTypeByBarCode("0123456789104").getQuantity();
        boolean check = ezShop.updateQuantity(pId,-10);
        assertFalse(check);
    }

    @Test
    public void TestUpdateQuantityLocationNotAssigned() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        boolean check = ezShop.updateQuantity(pId,10);
        assertFalse(check);
    }

    @Test
    public void TestUpdateQuantityInvalidProductId() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.updatePosition(pId,"1-A-1");
        assertThrows(InvalidProductIdException.class,()->ezShop.updateQuantity(-1,10));
        assertThrows(InvalidProductIdException.class,()->ezShop.updateQuantity(null,10));
    }

    @Test
    public void TestUpdateQuantityNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.logout();
        assertThrows(UnauthorizedException.class,()->ezShop.updateQuantity(pId,10));
    }

    @Test
    public void TestUpdateQuantityWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.logout();
        ezShop.createUser("user2","user2","Cashier");
        ezShop.login("user2","user2");
        assertThrows(UnauthorizedException.class,()->ezShop.updateQuantity(pId,10));
    }
}
