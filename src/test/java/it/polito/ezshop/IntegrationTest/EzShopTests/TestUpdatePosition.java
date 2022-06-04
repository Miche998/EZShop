package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUpdatePosition {
    EZShop ezShop = new EZShop();
    @Test
    public void TestUpdatePositionCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        boolean check = ezShop.updatePosition(pId,"1-A-1");
        assertEquals("1-A-1",ezShop.getProductTypeByBarCode("0123456789104").getLocation());
        assertTrue(check);
    }

    @Test
    public void TestUpdatePositionProductNotExists() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        boolean check = ezShop.updatePosition(2,"1-A-1");
        assertFalse(check);
    }

    @Test
    public void TestUpdatePositionSameLocation() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.updatePosition(pId,"1-A-1");
        Integer pId2 = ezShop.createProductType("product2","01234567891040",15.00,"note2");
        boolean check = ezShop.updatePosition(pId2,"1-A-1");
        assertFalse(check);
    }

    @Test
    public void TestUpdatePositionInvalidProductId() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidProductIdException.class,()->ezShop.updatePosition(-1,"1-A-1"));
        assertThrows(InvalidProductIdException.class,()->ezShop.updatePosition(null,"1-A-1"));
    }

    @Test
    public void TestUpdatePositionInvalidLocation() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        assertThrows(InvalidLocationException.class,()->ezShop.updatePosition(pId,"1A1"));
        assertThrows(InvalidLocationException.class,()->ezShop.updatePosition(pId,"1-2-3"));
        assertThrows(InvalidLocationException.class,()->ezShop.updatePosition(pId,"1-2-C"));
    }

    @Test
    public void TestUpdatePositionNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        assertThrows(UnauthorizedException.class,()->ezShop.updatePosition(pId,"1-A-1"));
    }

    @Test
    public void TestUpdatePositionWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        ezShop.createUser("user2","user2","Cashier");
        ezShop.login("user2","user2");
        assertThrows(UnauthorizedException.class,()->ezShop.updatePosition(pId,"1-A-1"));
    }
}
