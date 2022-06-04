package it.polito.ezshop.IntegrationTest.EzShopTests;
import it.polito.ezshop.data.Customer;

import java.util.ArrayList;
import java.util.List;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGetCustomers {
    EZShop ezShop = new EZShop();

    @Test
    public void TestGetCustomerCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        Customer customer = ezShop.getCustomer(Cid);
        assertSame(customer.getId(),Cid);
    }

    @Test
    public void TestGetCustomerWithCard() throws InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,Cid);
        Customer customer = ezShop.getCustomer(1);
        assertSame(customer.getId(),Cid);
        assertEquals(customer.getCustomerCard(),card);
    }

    @Test
    public void TestGetCustomerNonExistingCustomer() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerIdException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Customer customer = ezShop.getCustomer(1);
        assertNull(customer);
    }

    @Test
    public void TestGetCustomerNoUserLogged() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.getCustomer(Cid));
    }

    @Test
    public void TestGetCustomerInvalidId() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.defineCustomer("Zlatan");
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.getCustomer(-1));
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.getCustomer(0));
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.getCustomer(null));
    }

    @Test
    public void TestGetAllCustomersCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid1 = ezShop.defineCustomer("Zlatan");
        Integer Cid2 = ezShop.defineCustomer("Romelu");
        List<Customer> customers = ezShop.getAllCustomers();
        assertSame(customers.get(0).getId(),Cid1);
        assertSame(customers.get(1).getId(),Cid2);
    }

    @Test
    public void TestGetAllCustomersWithCard() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid1 = ezShop.defineCustomer("Zlatan");
        String card1 = ezShop.createCard();
        ezShop.attachCardToCustomer(card1,Cid1);
        Integer Cid2 = ezShop.defineCustomer("Luke");
        List<Customer> customers = ezShop.getAllCustomers();
        assertSame(customers.get(0).getId(),Cid1);
        assertSame(customers.get(1).getId(),Cid2);
        assertEquals(customers.get(0).getCustomerCard(),card1);
    }

    @Test
    public void TestGetAllCustomersNoCustomer() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        List<Customer> customers = ezShop.getAllCustomers();
        assertTrue(customers.isEmpty());
    }

    @Test
    public void TestGetAllCustomersNoUserLogged(){
        ezShop.reset();
        assertThrows(UnauthorizedException.class, ()-> ezShop.getAllCustomers());
    }
}
