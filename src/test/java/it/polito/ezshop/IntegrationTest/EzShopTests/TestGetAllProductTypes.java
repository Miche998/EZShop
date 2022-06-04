package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestGetAllProductTypes {
    EZShop ezShop = new EZShop();

    @Test
    public void TestGetAllProductTypesCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        Integer pId2 = ezShop.createProductType("product2","01234567891019",20.00,"note2");

        List<ProductType> products = new ArrayList<>();
        products = ezShop.getAllProductTypes();
        assertSame(pId,products.get(0).getId());
        assertSame(pId2,products.get(1).getId());
    }
    @Test
    public void TestGetAllProductTypesEmpty() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");

        List<ProductType> products = new ArrayList<>();
        products = ezShop.getAllProductTypes();
        assertTrue(products.isEmpty());
    }

    @Test
    public void TestGetAllProductTypesNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        assertThrows(UnauthorizedException.class,()->ezShop.getAllProductTypes());
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
        assertThrows(UnauthorizedException.class,()->ezShop.getAllProductTypes());
    }

    @Test
    public void TestGetProductTypeByBarcode() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        Integer pId2 = ezShop.createProductType("product2","01234567891019",20.00,"note2");

        assertSame(pId,ezShop.getProductTypeByBarCode("0123456789104").getId());
    }

    @Test
    public void TestGetProductTypeByBarcodeWrong() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        assertNull(ezShop.getProductTypeByBarCode("0123456789104"));
    }

    @Test
    public void TestGetProductTypebyBarcodeInvalidProductCode() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidProductCodeException.class,()->ezShop.getProductTypeByBarCode("0123456789103"));
        assertThrows(InvalidProductCodeException.class,()->ezShop.getProductTypeByBarCode(""));
        assertThrows(InvalidProductCodeException.class,()->ezShop.getProductTypeByBarCode(null));
        assertThrows(InvalidProductCodeException.class,()->ezShop.getProductTypeByBarCode("ci73o"));
    }

    @Test
    public void TestGetProductTypeByBarcodeNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        assertThrows(UnauthorizedException.class,()->ezShop.getProductTypeByBarCode("0123456789104"));
    }

    @Test
    public void TestGetProductTypeByBarcodeWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        ezShop.createUser("user2","user2","Cashier");
        ezShop.login("user2","user2");
        assertThrows(UnauthorizedException.class,()->ezShop.getProductTypeByBarCode("0123456789104"));
    }

    @Test
    public void TestGetProductTypesByDescription() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        Integer pId2 = ezShop.createProductType("product2","01234567891019",20.00,"note2");
        List <ProductType> products = new ArrayList<>();
        products = ezShop.getProductTypesByDescription("product");
        assertEquals("product",products.get(0).getProductDescription());
        assertEquals("product2",products.get(1).getProductDescription());
    }

    @Test
    public void TestGetProductTypesByDescriptionEmpty() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("Luke","user","Administrator");
        ezShop.login("Luke","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        Integer pId2 = ezShop.createProductType("product2","01234567891019",20.00,"note2");
        List <ProductType> products = new ArrayList<>();
        products = ezShop.getProductTypesByDescription(null);
        assertEquals("product",products.get(0).getProductDescription());
        assertEquals("product2",products.get(1).getProductDescription());
    }

    @Test
    public void TestGetProductTypesByDescriptionNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        assertThrows(UnauthorizedException.class,()->ezShop.getProductTypesByDescription(""));
    }

    @Test
    public void TestGetProductTypesByDescriptionWrongRole() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pId = ezShop.createProductType("product","0123456789104",15.00,"note");
        ezShop.logout();
        ezShop.createUser("user2","user2","Cashier");
        ezShop.login("user2","user2");
        assertThrows(UnauthorizedException.class,()->ezShop.getProductTypesByDescription(""));
    }
}
