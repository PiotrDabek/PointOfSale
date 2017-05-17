package pointofsale;

import java.math.BigDecimal;
import javax.swing.table.DefaultTableModel;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class POSFormTest {
    
    public POSFormTest() {
    }
    
    /**
     * Test of addElementToTable method, of class POSForm.
     * Test sprawdza czy do tabeli dodawany jest nowy element
     */
    @Test
    public void testAddElementToTable() {
        System.out.println("addElementToTable");
        Product tmp = new Product("produkt", "1111", "1.00");
        OrderItem item = new OrderItem(tmp);
        POSForm instance = new POSForm();
        instance.addElementToTable(item);
        int expectedResult = 1;
        int result = instance.getjTable1().getRowCount();
        assertEquals(expectedResult, result);
    }

    /**
     * Test of addExistingElementToTable method, of class POSForm.
     * Test sprawdza czy po dodaniu juz istniejacego elementu nie zwieksza sie ilość rzedow w tabeli
     */
    @Test
    public void testAddExistingElementToTable_RowCount() {
        System.out.println("addExistingElementToTable_RowCount");
        Product tmp = new Product("produkt", "1111", "1.00");
        OrderItem item = new OrderItem(tmp);
        POSForm instance = new POSForm();
        instance.addElementToTable(item);
        instance.addExistingElementToTable(item);
        int expectedResult = 1;
        int result =  instance.getjTable1().getRowCount();
        assertEquals(expectedResult, result);
    }
    
        /**
     * Test of addExistingElementToTable method, of class POSForm.
     * Test sprawdza, czy dodany ponownie element pojawia się ze zwiększoną iloscia
     */
    @Test
    public void testAddExistingElementToTable_Quantity() {
        System.out.println("addExistingElementToTable_Quantity");
        Product tmp = new Product("produkt", "1111", "1.00");
        OrderItem item = new OrderItem(tmp);
        POSForm instance = new POSForm();
        
        instance.addElementToTable(item);
        item.addQuantity();
        instance.addExistingElementToTable(item);
        
        DefaultTableModel model = (DefaultTableModel) instance.getjTable1().getModel();        
        
        String expectedResult = "2";
        String result =  model.getValueAt(0, 2).toString();
        assertEquals(expectedResult, result);
    }

    /**
     * Test of setTotal method, of class POSForm.
     * Test sprawdza, czy wyliczana wartosc total jest poprawna
     */
    @Test
    public void testSetTotal() {
        System.out.println("setTotal");
        BigDecimal bd = new BigDecimal("10");
        POSForm instance = new POSForm();
        instance.setTotal(bd);
        String expectedResult = "10";
        String result = instance.getjLabel2().getText();
        assertEquals(expectedResult, result);
    }

    /**
     * Test of reset method, of class POSForm.
     * Test pozwala spradzic, czy okno jest poprawnie przywracane do stanu poczatkowego - test tabeli
     */
    @Test
    public void testReset_RowCount() {
        System.out.println("reset");
        POSForm instance = new POSForm();
        Product tmp = new Product("produkt", "1111", "1.00");
        OrderItem item = new OrderItem(tmp);
        instance.addElementToTable(item); 
        
        tmp = new Product("produkt2", "2222", "2.00");
        item = new OrderItem(tmp);
        instance.addElementToTable(item);         
        
        instance.reset();
        
        int expectedResult = 0;
        int result = instance.getjTable1().getRowCount();
        
        assertEquals(expectedResult, result);
    }
    
    /**
     * Test of reset method, of class POSForm.
     * Test pozwala spradzic, czy okno jest poprawnie przywracane do stanu poczatkowego - test tabeli
     */
    @Test
    public void testReset_Total() {
        System.out.println("reset");
        POSForm instance = new POSForm();
        Product tmp = new Product("produkt", "1111", "1.00");
        OrderItem item = new OrderItem(tmp);
        instance.addElementToTable(item); 
        
        tmp = new Product("produkt2", "2222", "2.00");
        item = new OrderItem(tmp);
        instance.addElementToTable(item);         
        
        instance.reset();
        
        String expectedResult = "0";
        String result = instance.getjLabel2().getText();
        
        assertEquals(expectedResult, result);
    }
    
}
