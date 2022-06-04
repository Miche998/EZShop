package it.polito.ezshop.UnitTest;

import it.polito.ezshop.data.EZShop;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestEzShopAlgorithms {

    private EZShop shop = new EZShop();

    @Test
    public void TestCheckLuhn(){
        String cardNo = "123456789007";
        boolean check = shop.checkLuhn(cardNo);
        assertTrue(check);
    }
    @Test
    public void TestCheckLuhn2(){
        String cardNo = null;
        boolean check = shop.checkLuhn(cardNo);
        assertFalse(check);
    }
    @Test
    public void TestCheckLuhn3(){
        String cardNo = "";
        boolean check = shop.checkLuhn(cardNo);
        assertFalse(check);
    }

    @Test
    public void TestCheckLuhn4(){
        String cardNo = "12345d789f07";
        boolean check = shop.checkLuhn(cardNo);
        assertFalse(check);
    }

    @Test
    public void TestCheckLuhn5(){
        String cardNo = "123456789006";
        boolean check = shop.checkLuhn(cardNo);
        assertFalse(check);
    }

    @Test
    public void TestProductCodeAlgorithm(){
        String pCode = "0123456789104";
        boolean check = shop.productCodeAlgorithm(pCode);
        assertTrue(check);
    }

    @Test
    public void TestProductCodeAlgorithm2(){
        String pCode = null;
        boolean check = shop.productCodeAlgorithm(pCode);
        assertFalse(check);
    }

    @Test
    public void TestProductCodeAlgorithm3(){
        String pCode = "6291041500220";
        boolean check = shop.productCodeAlgorithm(pCode);
        assertTrue(check);
    }

    @Test
    public void TestProductCodeAlgorithm4(){
        String pCode = "6291041500221";
        boolean check = shop.productCodeAlgorithm(pCode);
        assertFalse(check);
    }

    @Test
    public void TestProductCodeAlgorithm5(){
        String pCode = "";
        boolean check = shop.productCodeAlgorithm(pCode);
        assertFalse(check);
    }

    @Test
    public void TestProductCodeAlgorithm6(){
        String pCode = "0123c567f910";
        boolean check = shop.productCodeAlgorithm(pCode);
        assertFalse(check);
    }
    @Test
    public void TestProductCodeAlgorithm7(){
        String pCode = "629104150024";
        boolean check = shop.productCodeAlgorithm(pCode);
        assertTrue(check);
    }


}
