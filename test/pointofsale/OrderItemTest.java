/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointofsale;

import java.math.BigDecimal;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class OrderItemTest {
    
    public OrderItemTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getTotal method, of class OrderItem.
     */
    @Test
    public void testGetTotal() {
        System.out.println("getTotal");
        Product tmp = new Product("produkt", "1111", "1.00");
        OrderItem instance = new OrderItem(tmp);
        instance.addQuantity();
        BigDecimal expResult = new BigDecimal("2.00");
        BigDecimal result = instance.getTotal();
        assertEquals(expResult, result);
    }

    /**
     * Test of addQuantity method, of class OrderItem.
     */
    @Test
    public void testAddQuantity() {
        System.out.println("addQuantity");
        Product tmp = new Product("produkt", "1111", "1.00");
        OrderItem instance = new OrderItem(tmp);
        instance.addQuantity();
        int expResult = 2;
        int result = instance.getQuantity();
        assertEquals(expResult, result);
    }
    
}
