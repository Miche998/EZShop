package it.polito.ezshop.IntegrationTest.EzShopTests;

import it.polito.ezshop.data.*;
import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.ReturnTransactionImpl;
import it.polito.ezshop.model.SaleTransactionImpl;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class TestEndReturnTransaction {
    EZShop ezShop = new EZShop();

    @Test
    public void TestEndReturnTransactionCorrectCommit() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
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
        boolean res = ezShop.endReturnTransaction(rid, true);
        assertTrue(res);
        assertSame(47, ezShop.getProductTypeByBarCode("123456789104").getQuantity());
        SaleTransaction s = ezShop.getSaleTransaction(tid);
        for(TicketEntry t : s.getEntries()){
            if(t.getBarCode().equals("123456789104")){
                assertSame(3,t.getAmount());
            }
        }
    }

    @Test
    public void TestEndReturnTransactionCorrectNoCommit() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
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
        boolean res = ezShop.endReturnTransaction(rid, false);
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
    public void TestEndReturnTransactionUnauthorized() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidPasswordException, InvalidRoleException, InvalidUsernameException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidPaymentException {
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
        ezShop.logout();
        assertThrows(UnauthorizedException.class, ()-> ezShop.endReturnTransaction(rid,true));
    }


    @Test
    public void TestEndInvalidReturnTransaction() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidPaymentException {
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
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.endReturnTransaction(-1,true));
        assertThrows(InvalidTransactionIdException.class, ()-> ezShop.endReturnTransaction(null,true));
    }

    @Test
    public void TestEndReturnTransactionNotExistent() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException, UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductCodeException, InvalidLocationException, InvalidProductIdException, InvalidQuantityException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidPaymentException {
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
        boolean res = ezShop.endReturnTransaction(rid+1,true);
        assertFalse(res);
    }

}
