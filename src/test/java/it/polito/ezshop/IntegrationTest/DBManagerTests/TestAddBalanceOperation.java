package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestAddBalanceOperation {
    DBManager db = new DBManager();

    @Test
    public void TestAddBalanceOperationCorrect(){
        db.reset();
        Integer bId = db.addBalanceOperation(10.0);
        assertSame(1,bId);
    }

    @Test
    public void TestGetCreditsAndDebitsCorrect(){
        db.reset();
        Integer bId = db.addBalanceOperation(10.0);
        Integer bId2 = db.addBalanceOperation(50.0);
        Integer bId3 = db.addBalanceOperation(-20.0);

        List<BalanceOperation> balances = new ArrayList<>();
        String from = "2021-05-10";
        String to = "2021-06-30";
        LocalDate fromD = LocalDate.parse(from);
        LocalDate toD = LocalDate.parse(to);
        balances = db.getCreditsAndDebits(fromD,toD);
        assertSame(balances.get(0).getBalanceId(),bId);
        assertSame(balances.get(1).getBalanceId(),bId2);
        assertSame(balances.get(2).getBalanceId(),bId3);
    }

    @Test
    public void TestGetCreditsAndDebitsInvertedDate(){
        db.reset();
        Integer bId = db.addBalanceOperation(10.0);
        Integer bId2 = db.addBalanceOperation(50.0);
        Integer bId3 = db.addBalanceOperation(100.0);

        List<BalanceOperation> balances = new ArrayList<>();
        String to = "2021-05-10";
        String from = "2021-06-30";
        LocalDate fromD = LocalDate.parse(from);
        LocalDate toD = LocalDate.parse(to);
        balances = db.getCreditsAndDebits(toD,fromD);
        assertSame(balances.get(0).getBalanceId(),bId);
        assertSame(balances.get(1).getBalanceId(),bId2);
        assertSame(balances.get(2).getBalanceId(),bId3);
    }

    @Test
    public void TestGetCreditsAndDebitsEmptyList(){
        db.reset();
        Integer bId = db.addBalanceOperation(10.0);
        Integer bId2 = db.addBalanceOperation(50.0);
        Integer bId3 = db.addBalanceOperation(100.0);

        List<BalanceOperation> balances = new ArrayList<>();
        String from = "2022-05-24";
        String to = "2022-05-23";
        LocalDate fromD = LocalDate.parse(from);
        LocalDate toD = LocalDate.parse(to);
        balances = db.getCreditsAndDebits(toD,fromD);
        assertTrue(balances.isEmpty());
    }

    @Test
    public void TestGetCreditsAndDebitsFrom_Null(){
        db.reset();
        Integer bId = db.addBalanceOperation(10.0);
        Integer bId2 = db.addBalanceOperation(50.0);
        Integer bId3 = db.addBalanceOperation(100.0);

        List<BalanceOperation> balances = new ArrayList<>();
        LocalDate from = null;
        String to = "2021-06-30";
        LocalDate toD = LocalDate.parse(to);
        balances = db.getCreditsAndDebits(from,toD);
        assertSame(balances.get(0).getBalanceId(),bId);
        assertSame(balances.get(1).getBalanceId(),bId2);
        assertSame(balances.get(2).getBalanceId(),bId3);
    }

    @Test
    public void TestGetCreditsAndDebitsTo_null(){
        db.reset();
        Integer bId = db.addBalanceOperation(10.0);
        Integer bId2 = db.addBalanceOperation(50.0);
        Integer bId3 = db.addBalanceOperation(100.0);

        List<BalanceOperation> balances = new ArrayList<>();
        String from = "2021-05-20";
        LocalDate fromD = LocalDate.parse(from);
        LocalDate toD = null;
        balances = db.getCreditsAndDebits(fromD,toD);
        assertSame(balances.get(0).getBalanceId(),bId);
        assertSame(balances.get(1).getBalanceId(),bId2);
        assertSame(balances.get(2).getBalanceId(),bId3);
    }

    @Test
    public void TestGetCreditsAndDebitAllNull(){
        db.reset();
        Integer bId = db.addBalanceOperation(10.0);
        Integer bId2 = db.addBalanceOperation(50.0);
        Integer bId3 = db.addBalanceOperation(100.0);

        List<BalanceOperation> balances = new ArrayList<>();
        LocalDate fromD = null;
        LocalDate toD = null;
        balances = db.getCreditsAndDebits(toD,fromD);
        assertSame(balances.get(0).getBalanceId(),bId);
        assertSame(balances.get(1).getBalanceId(),bId2);
        assertSame(balances.get(2).getBalanceId(),bId3);
    }
}
