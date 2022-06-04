package it.polito.ezshop.UnitTest;

import it.polito.ezshop.model.UserImpl;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestUserImpl {
    UserImpl u = new UserImpl(10, "andrea","aho","Administrator");

    @Test
    public void testSetId(){
        u.setId(12);
        Integer i = u.getId();

        assertSame(12,i);

    }

    @Test
    public void testSetUsername(){
        u.setUsername("Ciccio");
        String s = u.getUsername();

        assertEquals("Ciccio", s);
    }

    @Test
    public void testSetPassword(){
        u.setPassword("secret");
        String s = u.getPassword();

        assertEquals("secret", s);
    }

    @Test
    public void testSetRole(){
        u.setRole("Cashier");
        String s = u.getRole();

        assertEquals("Cashier", s);
    }

}
