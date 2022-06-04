package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCreateProductType {
    EZShop ezShop = new EZShop();

    @Test
    public void TestCreateProductTypeCorrect() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        assertSame(1,pId);
    }
    @Test
    public void TestCreateProductTypeWrong() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        Integer pId2 = ezShop.createProductType("product2","0123456789104",20.00,"note2");
        assertSame(-1,pId2);
    }

    @Test
    public void TestCreateProductTypeNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        assertThrows(UnauthorizedException.class,()->ezShop.createProductType("product","0123456789104",15.00,"note"));
    }

    @Test
    public void TestCreateProductTypeWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Cashier");
        ezShop.login("user","user");
        assertThrows(UnauthorizedException.class,()->ezShop.createProductType("product","0123456789104",15.00,"note"));
    }

    @Test
    public void TestCreateProductTypeNullOrEmptyDescription() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidProductDescriptionException.class,()->ezShop.createProductType("","0123456789104",15.00,"note"));
        assertThrows(InvalidProductDescriptionException.class,()->ezShop.createProductType(null,"0123456789104",15.00,"note"));
    }

    @Test
    public void TestCreateProductTypeNegativePricePerUnit() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidPricePerUnitException.class,()->ezShop.createProductType("description","0123456789104",0.00,"note"));
    }

    @Test
    public void TestCreateProductTypeNullOrEmptyProductCode() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidProductCodeException.class,()->ezShop.createProductType("description","",15.00,"note"));
        assertThrows(InvalidProductCodeException.class,()->ezShop.createProductType("description",null,15.00,"note"));
    }

    @Test
    public void TestCreateProductTypeWrongLengthProductCode() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidProductCodeException.class,()->ezShop.createProductType("description","012345678910478",15.00,"note"));
        assertThrows(InvalidProductCodeException.class,()->ezShop.createProductType("description","01234567891",15.00,"note"));
        assertThrows(InvalidProductCodeException.class,()->ezShop.createProductType("description","012345678911",15.00,"note"));
        assertThrows(InvalidProductCodeException.class,()->ezShop.createProductType("description","ciao",15.00,"note"));
    }

    @Test
    public void TestCreateProductTypeNullNote() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,null);
        assertEquals("",ezShop.getProductTypeByBarCode("0123456789104").getNote());
    }

}
