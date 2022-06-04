package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.Customer;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestModifyCustomer {
    EZShop ezShop = new EZShop();

    @Test
    public void TestModifyCustomerCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,Cid);
        String NewCard = ezShop.createCard();
        ezShop.modifyCustomer(Cid,"user2",NewCard);
        assertEquals("user2",ezShop.getCustomer(Cid).getCustomerName());
        assertEquals(NewCard,ezShop.getCustomer(Cid).getCustomerCard());
    }

    @Test
    public void TestModifyCustomerWrongCustomerName() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        assertThrows(InvalidCustomerNameException.class, ()-> ezShop.modifyCustomer(Cid,null,"1234567890"));
        assertThrows(InvalidCustomerNameException.class, ()-> ezShop.modifyCustomer(Cid,"","1234567890"));
    }

    @Test
    public void TestModifyCustomerWrongCid() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.modifyCustomer(null,"Zlatan","1234567890"));
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.modifyCustomer(-5, "Zlatan","1234567890"));
        assertThrows(InvalidCustomerIdException.class, ()-> ezShop.modifyCustomer(0,"Zlatan","1234567890"));
    }

    @Test
    public void TestModifyCustomerNoUserLogged() throws InvalidCustomerNameException, UnauthorizedException, InvalidPasswordException, InvalidUsernameException, InvalidRoleException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.modifyCustomer(Cid,"Zlatan","1234567890"));
    }

    @Test
    public void TestModifyCustomerNonExistingCustomer() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        String card = ezShop.createCard();
        boolean check = ezShop.modifyCustomer(1,"Zlatan",card);
        assertFalse(check);
    }

    @Test
    public void TestModifyCustomerNonExistingCard() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,Cid);
        boolean check = ezShop.modifyCustomer(1,"Zlatan","1000000000");
        assertFalse(check);
    }

    @Test
    public void TestModifyCustomerWrongCardFormat() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerIdException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer cId = ezShop.defineCustomer("Mark");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,cId);
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.modifyCustomer(cId,"Zlatan","10A00A0000"));
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.modifyCustomer(cId,"Zlatan","123456789"));

    }

    @Test
    public void TestModifyCustomerModifyNameOnly() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,Cid);
        ezShop.modifyCustomer(Cid,"Zlatana",null);
        Customer customer = ezShop.getCustomer(Cid);
        assertEquals("Zlatana",customer.getCustomerName());
        assertEquals(card,customer.getCustomerCard());
    }

    @Test
    public void TestModifyCustomerModifyNameAndRemoveCard() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,Cid);
        ezShop.modifyCustomer(Cid,"Zlatana","");
        Customer customer = ezShop.getCustomer(Cid);
        assertEquals("Zlatana",customer.getCustomerName());
        assertNull(customer.getCustomerCard());
    }

    @Test
    public void TestModifyCustomerExistingCard() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidCustomerNameException, UnauthorizedException, InvalidCustomerIdException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid1 = ezShop.defineCustomer("Zlatan");
        Integer Cid2 = ezShop.defineCustomer("Romelu");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,Cid1);
        boolean check = ezShop.modifyCustomer(Cid2,"Romelu",card);
        assertFalse(check);
    }
}
