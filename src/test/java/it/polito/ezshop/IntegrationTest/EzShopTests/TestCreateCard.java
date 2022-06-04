package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCreateCard {
    EZShop ezShop = new EZShop();

    @Test
    public void TestCreateCardCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        String card = ezShop.createCard();

        assertTrue(card.matches("[0-9]+"));
        assertEquals(10,card.length());
        assertNotEquals("", card);
    }

    @Test
    public void TestCreateCardNoUserLogged(){
        ezShop.reset();
        assertThrows(UnauthorizedException.class, ()-> ezShop.createCard());
    }
}
