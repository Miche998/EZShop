package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.Order;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestGetAllOrders {
    EZShop ezShop = new EZShop();

    @Test
    public void TestGetAllOrdersNoLoggedUser() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        Integer pId = ezShop.createProductType("shoes","1234567890678",5.00,"dude");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.updateQuantity(pId,100);

        ezShop.issueOrder("1234567890678",10,5.00);
        ezShop.issueOrder("1234567890678",20,5.00);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.getAllOrders());
        ezShop.createUser("user1","user1","Cashier");
        ezShop.login("user1","user1");
        assertThrows(UnauthorizedException.class, ()-> ezShop.getAllOrders());
    }

    @Test
    public void TestGetAllOrdersCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        Integer pId = ezShop.createProductType("shoes","1234567890678",5.00,"dude");
        ezShop.updatePosition(pId,"1-A-1");
        ezShop.updateQuantity(pId,100);

        assertSame(0,ezShop.getAllOrders().size());

        Integer oId = ezShop.issueOrder("1234567890678",10,5.00);
        Integer oId2 = ezShop.issueOrder("1234567890678",20,5.00);

        List<Order> orders =  ezShop.getAllOrders();

        assertSame(oId,orders.get(0).getOrderId());
        assertSame(oId2,orders.get(1).getOrderId());
        assertSame(2,orders.size());
    }
}
