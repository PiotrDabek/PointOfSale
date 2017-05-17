package pointofsale;

import java.math.BigDecimal;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class PointOfSaleTest {
    
    public PointOfSaleTest() {
    }
    /**
     * Test of addToReceipt method, of class PointOfSale.
     * Test sprawdza dodawanie elementu w przypadku, gdy nie znajduje sie on na liscie juz dodanych produktow
     */
    @Test
    public void testAddToReceipt_NotContaining() {
        System.out.println("addToReceipt_NotContaining");
        Product item = new Product("produkt", "1111", "1.00");
        
        PointOfSale POS = new PointOfSale();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            InputMocker input = new InputMocker(POS);
            }
        }); 
        
        POS.addToReceipt(item);
        
        int expectedValue = 1;
        int result = POS.getOrderList().size();
        System.out.println(POS.getOrderList().size());
        assertEquals(expectedValue, result);        
    }
    
    
        /**
     * Test of addToReceipt method, of class PointOfSale.
     * Test sprawdza dodawanie elementu w przypadku, znajduje sie on na liscie juz dodanych produktow
     */
    @Test
    public void testAddToReceipt_Containing() {
        System.out.println("addToReceipt_Containing");
        Product item = new Product("produkt", "1111", "1.00");
        
        PointOfSale POS = new PointOfSale();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            InputMocker input = new InputMocker(POS);
            }
        }); 
           
        POS.addToReceipt(item);
        POS.addToReceipt(item);
        
        int expectedValue = 1;
        int result = POS.getOrderList().size();
        System.out.println(POS.getOrderList().size());
        assertEquals(expectedValue, result);        
    }
}
