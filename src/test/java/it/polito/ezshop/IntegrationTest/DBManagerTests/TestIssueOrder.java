package it.polito.ezshop.IntegrationTest.DBManagerTests;

import it.polito.ezshop.model.OrderImpl;
import it.polito.ezshop.model.ProductTypeImpl;
import it.polito.ezshop.util.DBManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestIssueOrder {
    DBManager db = new DBManager();

    @Test
    public void TestIssueOrderWrong(){
        db.reset();
        Integer oId = db.issueOrder("123456789104",10,20.0);

        assertSame(-1, oId);

    }

    @Test
    public void TestIssueOrderCorrect(){
        db.reset();
        Integer pId = db.addProduct("description", "123456789104", 20.0, "note");
        Integer oId = db.issueOrder("123456789104",10,20.0);

        assertSame(1, oId);
    }

}
