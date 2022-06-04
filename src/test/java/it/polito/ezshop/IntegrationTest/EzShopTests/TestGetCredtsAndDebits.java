package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.InvalidPasswordException;
import it.polito.ezshop.exceptions.InvalidRoleException;
import it.polito.ezshop.exceptions.InvalidUsernameException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestGetCredtsAndDebits {

    EZShop ezShop = new EZShop();

    @Test
    public void TestGetCreditsAndDebits() throws UnauthorizedException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");

        ezShop.recordBalanceUpdate(100);
        ezShop.recordBalanceUpdate(-50);
        ezShop.recordBalanceUpdate(120);

        List<BalanceOperation> operations = new ArrayList<>();
        operations = ezShop.getCreditsAndDebits(null,null);
        assertEquals(100.0,operations.get(0).getMoney(),0.0);
        assertEquals(-50.0,operations.get(1).getMoney(),0.0);
        assertEquals(120.0,operations.get(2).getMoney(),0.0);

        String date = "30-06-2021";
        LocalDate d = LocalDate.now().plusDays(1);
        operations = ezShop.getCreditsAndDebits(d,null);
        assertTrue(operations.isEmpty());

        operations = ezShop.getCreditsAndDebits(LocalDate.now(),LocalDate.now().plusDays(1));
        assertEquals(100.0,operations.get(0).getMoney(),0.0);
        assertEquals(-50.0,operations.get(1).getMoney(),0.0);
        assertEquals(120.0,operations.get(2).getMoney(),0.0);

        operations = ezShop.getCreditsAndDebits(LocalDate.now().plusDays(1),LocalDate.now());
        assertEquals(100.0,operations.get(0).getMoney(),0.0);
        assertEquals(-50.0,operations.get(1).getMoney(),0.0);
        assertEquals(120.0,operations.get(2).getMoney(),0.0);
    }

    @Test
    public void TestGetCreditsAndDebitsUnauthorizedException() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {
        ezShop.reset();
        ezShop.createUser("user", "user", "Cashier");
        assertThrows(UnauthorizedException.class,()->ezShop.getCreditsAndDebits(null,null));
    }
}
