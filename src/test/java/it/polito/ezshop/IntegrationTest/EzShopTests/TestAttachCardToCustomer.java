package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAttachCardToCustomer {
    EZShop ezShop = new EZShop();

    @Test
    public void TestAttachCardToCustomerCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        boolean check = ezShop.attachCardToCustomer(card, Cid);
        assertTrue(check);
        assertEquals(card, ezShop.getCustomer(Cid).getCustomerCard());
    }

    @Test
    public void TestAttachCardToCustomerCidNullOrNegative() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.attachCardToCustomer(card,null));
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.attachCardToCustomer(card, -5));
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.attachCardToCustomer(card, 0));
    }

    @Test
    public void TestAttachCardToCustomerCardWrongCardNumber() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.attachCardToCustomer(null, Cid));
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.attachCardToCustomer("",Cid));
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.attachCardToCustomer("123456789",Cid));
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.attachCardToCustomer("123456789Z",Cid));
    }

    @Test
    public void TestAttachCardToCustomerNoUserLogged() throws UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.attachCardToCustomer(card,Cid));
    }

    @Test
    public void TestAttachCardToCustomerNonExistingCustomer() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        String card = ezShop.createCard();
        boolean check = ezShop.attachCardToCustomer(card,1);
        assertFalse(check);
    }

    @Test
    public void TestAttachCardToCustomerNonExistingCard() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException, InvalidCustomerNameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        boolean check = ezShop.attachCardToCustomer("1234567890",Cid);
        assertFalse(check);
    }

    @Test
    public void TestAttachCardToCustomerExistingCard() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid1 = ezShop.defineCustomer("Zlatan");
        Integer Cid2 = ezShop.defineCustomer("Romelu");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,Cid1);
        boolean check = ezShop.attachCardToCustomer(card,Cid2);
        assertFalse(check);
    }
}
