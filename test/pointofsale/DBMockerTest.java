/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointofsale;

import java.util.Map;
import java.util.TreeMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author szopen
 */
public class DBMockerTest {
    
    public DBMockerTest() {
    }
    
    /**
     * Test of selectProduct method, of class DBMocker.
     */
    @Test
    public void testSelectProduct() {
        System.out.println("selectProduct");
        String ID = "1111";
        DBMocker instance = new DBMocker();
        Product tmp = new Product("produkt", "1111", "1.00");
        instance.getDataMap().put(tmp.getProductID(),tmp);
        Map<String, String> expectedResult = new TreeMap<>();
        expectedResult.put("Name", tmp.getProductName());
        expectedResult.put("ID", tmp.getProductID());
        expectedResult.put("Price", tmp.getProductPrice().toString());
        Map<String, String> result = instance.selectProduct(ID);
        assertEquals(expectedResult, result);
    }
    
        /**
     * Test of selectProduct method, of class DBMocker.
     */
    @Test
    public void testSelectProduct_Null() {
        System.out.println("selectProduct");
        String ID = "2222";
        DBMocker instance = new DBMocker();
        Product tmp = new Product("produkt", "1111", "1.00");
        instance.getDataMap().put(tmp.getProductID(),tmp);
        Map<String, String> expectedResult = null;
        Map<String, String> result = instance.selectProduct(ID);
        assertEquals(expectedResult, result);
    }
    
}
