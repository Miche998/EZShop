package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUpdateProduct {
    EZShop ezShop = new EZShop();

    @Test
    public void TestUpdateProductTypeCorrect() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        boolean check = ezShop.updateProduct(pId,"newDescription","01234567891019",20.00,"note2");

        assertTrue(check);
        assertEquals("newDescription",ezShop.getProductTypeByBarCode("01234567891019").getProductDescription());
        assertEquals("note2",ezShop.getProductTypeByBarCode("01234567891019").getNote());
    }

    @Test
    public void TestUpdateProductTypeWrongBarCode() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        Integer pId2 = ezShop.createProductType("product2","01234567891019",20.00,"note2");

        boolean check = ezShop.updateProduct(pId2,"newDescription","0123456789104",20.00,"note2");
        assertFalse(check);
    }

    @Test
    public void TestUpdateProductTypeWrongId() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        boolean check = ezShop.updateProduct(2,"newDescription","01234567891019",20.00,"note2");
        assertFalse(check);
   }

    @Test
    public void TestUpdateProductTypeNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        assertThrows(UnauthorizedException.class,()->ezShop.updateProduct(pId,"product2","0123456789104",205.00,"note2"));
    }

    @Test
    public void TestUpdateProductTypeWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        ezShop.createUser("user2","user2","Cashier");
        ezShop.login("user2","user2");
        assertThrows(UnauthorizedException.class,()->ezShop.updateProduct(pId,"product2","0123456789104",205.00,"note2"));
    }

    @Test
    public void TestUpdateProductTypeNullOrEmptyDescription() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        assertThrows(InvalidProductDescriptionException.class,()->ezShop.updateProduct(pId,"","0123456789104",205.00,"note2"));
        assertThrows(InvalidProductDescriptionException.class,()->ezShop.updateProduct(pId,null,"0123456789104",205.00,"note2"));
    }



    @Test
    public void TestUpdateProductTypeNegativePricePerUnit() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        assertThrows(InvalidPricePerUnitException.class,()->ezShop.updateProduct(pId,"description2","0123456789104",0.00,"note2"));
    }

    @Test
    public void TestUpdateProductTypeNullOrEmptyProductCode() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        assertThrows(InvalidProductCodeException.class,()->ezShop.updateProduct(pId,"description2","",15.00,"note"));
        assertThrows(InvalidProductCodeException.class,()->ezShop.updateProduct(pId,"description2",null,15.00,"note"));
    }

    @Test
    public void TestUpdateProductTypeWrongLengthProductCode() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        assertThrows(InvalidProductCodeException.class,()->ezShop.updateProduct(pId,"description2","012345678910478",15.00,"note2"));
        assertThrows(InvalidProductCodeException.class,()->ezShop.updateProduct(pId,"description2","01234567891",15.00,"note2"));
        assertThrows(InvalidProductCodeException.class,()->ezShop.updateProduct(pId,"description2","012345678911",15.00,"note2"));
        assertThrows(InvalidProductCodeException.class,()->ezShop.updateProduct(pId,"description2","ciao",15.00,"note2"));
    }

    @Test
    public void TestUpdateProductTypeNullNote() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidProductIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.updateProduct(pId,"product2","0123456789104",100.00,null);
        assertEquals("",ezShop.getProductTypeByBarCode("0123456789104").getNote());
    }

    @Test
    public void TestUpdateProductTypeInvalidId() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        assertThrows(InvalidProductIdException.class,()->ezShop.updateProduct(-1,"description2","0123456789104",15.00,"note"));
        assertThrows(InvalidProductIdException.class,()->ezShop.updateProduct(null,"description2","0123456789104",15.00,"note"));
    }
}
