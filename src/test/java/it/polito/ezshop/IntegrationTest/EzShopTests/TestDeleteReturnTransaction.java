package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.exceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDeleteReturnTransaction {
    EZShop ezShop = new EZShop();

    @Test
    public void TestDeleteReturnTransactionCorrect() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        ezShop.returnProduct(rid,"123456789104",2);
        ezShop.endReturnTransaction(rid, true);
        boolean res = ezShop.deleteReturnTransaction(rid);
        assertTrue(res);
        assertSame(45, ezShop.getProductTypeByBarCode("123456789104").getQuantity());
        SaleTransaction s = ezShop.getSaleTransaction(tid);
        for(TicketEntry t : s.getEntries()){
            if(t.getBarCode().equals("123456789104")){
                assertSame(5,t.getAmount());
            }
        }
    }



    @Test
    public void TestDeleteReturnTransactionUnauthorized() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        ezShop.returnProduct(rid,"123456789104",2);
        ezShop.endReturnTransaction(rid,true);
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.deleteReturnTransaction(rid));
    }


    @Test
    public void TestDeleteInvalidReturnTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid,"123456789104",5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        ezShop.returnProduct(rid,"123456789104",2);
        ezShop.endReturnTransaction(rid,true);
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.deleteReturnTransaction(-1));
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.deleteReturnTransaction(null));
    }

    @Test
    public void TestDeleteReturnTransactionNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        ezShop.returnProduct(rid,"123456789104",2);
        ezShop.endReturnTransaction(rid,true);
        boolean res = ezShop.deleteReturnTransaction(rid+1);
        assertFalse(res);
    }

    @Test
    public void TestDeleteReturnTransactionAlreadyPayed() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
        ezShop.reset();
        ezShop.createUser("user","user","Administrator");
        ezShop.login("user","user");
        Integer pid = ezShop.createProductType("Description", "123456789104", 15.00, "Note");
        ezShop.updatePosition(pid, "1-A-1");
        ezShop.updateQuantity(pid, 50);
        Integer tid = ezShop.startSaleTransaction();
        ezShop.addProductToSale(tid, "123456789104", 5);
        ezShop.endSaleTransaction(tid);
        ezShop.receiveCashPayment(tid,100);
        Integer rid = ezShop.startReturnTransaction(tid);
        ezShop.returnProduct(rid,"123456789104",2);
        ezShop.endReturnTransaction(rid,true);
        ezShop.returnCashPayment(rid);
        boolean res = ezShop.deleteReturnTransaction(rid);
        assertFalse(res);
    }
}
