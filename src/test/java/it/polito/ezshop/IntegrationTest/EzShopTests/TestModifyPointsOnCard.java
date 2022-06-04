package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestModifyPointsOnCard {
    EZShop ezShop = new EZShop();

    @Test
    public void TestModifyPointsOnCardCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidCustomerCardException, InvalidCustomerNameException, InvalidCustomerIdException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,Cid);
        boolean check = ezShop.modifyPointsOnCard(card, 25);
        assertTrue(check);
        assertSame(25, ezShop.getCustomer(Cid).getPoints());
    }

    @Test
    public void TestModifyPointsOnCardWrongCardNumber() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.modifyPointsOnCard(null,25));
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.modifyPointsOnCard("", 25));
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.modifyPointsOnCard("123456789",25));
        assertThrows(InvalidCustomerCardException.class, ()-> ezShop.modifyPointsOnCard("123456789Z",25));
    }

    @Test
    public void TestModifyPointsOnCardNonExistingCard() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        boolean check = ezShop.modifyPointsOnCard("1234567890",25);
        assertFalse(check);
    }

    @Test
    public void TestModifyPointsOnCardNoUserLogged() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        String card = ezShop.createCard();
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.modifyPointsOnCard(card,25));
    }

    @Test
    public void TestModifyPointsOnCardNegativeTotalPoints() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidCustomerCardException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        String card = ezShop.createCard();
        ezShop.modifyPointsOnCard(card,5);
        boolean check = ezShop.modifyPointsOnCard(card, -10);
        assertFalse(check);
    }

    @Test
    public void TestModifyPointsOnCardZeroTotalPoints() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidCustomerCardException, InvalidCustomerIdException, InvalidCustomerNameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer Cid = ezShop.defineCustomer("Zlatan");
        String card = ezShop.createCard();
        ezShop.attachCardToCustomer(card,Cid);
        ezShop.modifyPointsOnCard(card,5);
        boolean check = ezShop.modifyPointsOnCard(card, -5);
        assertTrue(check);
        assertSame(0,ezShop.getCustomer(Cid).getPoints());
    }
}
