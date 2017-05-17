package pointofsale;

import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 * Klasa służąca do obsługi stanowiska POS. Produkty skanowane są za pomocą klasy
 * InputMocker działającej w osobnym wątku. Po sprawdzeniu poprawności wprowadzonych 
 * danych sprawdzane jest czy dany produkt znajduje się w klasie DBMocker.
 * @see DBMocker
 * @see InputMocker
 * Jeśli produkt nie znajduje się w bazie wyświetlany jest stosowny komunikat, podobnie
 * w przyadku, gdy wprowadzone dane są błędne(puste). W celu wyłączenia okien informacyjnych
 * należy nacisnąć przycisk Add Product. W celu zakończenia transakcji należy wpisać 
 * kod ---   exit   --- . Wpisanie tego polecenia powoduje zakończenie transakcji,
 * wyświetlenie i wydrukowanie rachunku. 
 * @author Piotr Dąbek
 */
public class PointOfSale {
   
    private Map<String, OrderItem> orderList;                                   // mapa przechowująca produkty na rachunku
    private POSForm posWindow;                                                  // główne okno programu
    private DBMocker dbMocker;                                                  // mocker bazy danych
    private ErrorForm errorForm = null;                                         // okno informacyjne
    private ReceiptForm receiptForm = null;                                     // okno rachunku
    private BigDecimal total;                                                   // należność za rachunek

    public PointOfSale() {
        posWindow = new POSForm();
        orderList = new TreeMap<>();
        dbMocker = new DBMocker();
        total = new BigDecimal("0");
    }

    
    
    public static void main(String[] args) {
        
        PointOfSale POS = new PointOfSale();                                    // stworzenie obiektu POS     
        POS.posWindow.setVisible(true);
        
        java.awt.EventQueue.invokeLater(new Runnable() {                        // InputMocker w nowym wątku
            @Override
            public void run() {
            InputMocker input = new InputMocker(POS);
            input.setVisible(true);
            }
        });        
    }

    /**
     * Metoda dodająca produkt do mapy produktów. Gdy obiekt znajduje się w mapie
     * nie jest on dodawny ponownie, natomiast zwiekszana jest jego ilość.
     * @param item produkt do dodania
     */
    public void addToReceipt(Product item)
    {
        if (orderList.containsKey(item.getProductID()))                         // czy produkt znajduje się w mapie
        {
            orderList.get(item.getProductID()).addQuantity();
            posWindow.addExistingElementToTable(orderList.get(item.getProductID()));
            
        }else
        {
            OrderItem tmp = new OrderItem(item);
            orderList.put(item.getProductID(), tmp );
            posWindow.addElementToTable(tmp);
        }
        total = total.add(item.getProductPrice());                              // wyliczenie nowej należności
        posWindow.setTotal(total);                                              // wyświetlenie należności
    }
    
    /**
     * Metoda służąca do obsługi zeskanowanego kodu kreskowego.
     * Metoda sprawdza poprawność kodu kreskowego i podejmuje odpowiednie działania 
     * gdy wprowadzony został kod  exit. Gdy kod został zeskanowany poprawnie 
     * wysyłane jest zapytanie do DBMockera czy produkt znajduje się w bazie.
     * JJeśli tak, dodawany jest on do mapy produktów, jesli nie - pojawia
     * się komunikat o błędzie.
     * @param barcode podany ID (kod kreskowy) produktu
     */
    public void scanProduct(String barcode)
    {
        if(barcode.equals(""))showErrorDialog("Błąd wprowadzania danych");      // wyswietlenie komunikatu    
        else if(barcode.equals("exit"))
        {
            if(orderList.isEmpty()){
                showErrorDialog("Rachunek jest pusty!");                        // sprawdzenie czy rachunnek nie jest pusty
            }else{
                printReceipt();                                                 // zakończenie transakcji
            }
        }
        else{
        Map<String, String> tmpMap = dbMocker.selectProduct(barcode);           // zapytanie do bazy
        if(tmpMap != null)                                                      // czy produkt znajduje się w bazie
        {
            Product tmpProduct = new Product(tmpMap.get("Name"), tmpMap.get("ID"), tmpMap.get("Price"));
            addToReceipt(tmpProduct);                                           // dodaj produkt do mapy produktów
        }
        else
        {
            showErrorDialog("Brak produktu " + barcode + " w bazie danych");    // wyswietlene informacji
        }
        }
    }
    
    /**
     * Klasa służąca do wyświetlenia okna informującego o błedzie
     * Okno zamykane jest poprzez wciśnięcie przycisku Add Product w klasie InputMocker
     * @param str tekst do wyświetlenia
     */
    private void showErrorDialog (String str)
    {
        errorForm = new ErrorForm();
        errorForm.getjLabel1().setText(str);
        errorForm.setLocationRelativeTo(null);
        errorForm.setVisible(true);
    }
    
    /**
     * Klasa służąca do wyświetlenia i wydrukowania rachunku. Rachunek jest wyświetlany
     * w formie graficznej w osobnym oknie zamykanym poprzez nasiśnięcie przycisku 
     * Add Product w oknie InputMocker. Rachunek jest jednocześnie drukowany. 
     * Po zakończeniu drukowania transakcja jest uznana za skończoną i obiekt 
     * POS przywracany jest do stanu początkowego.
     */
    private void printReceipt()
    {
        showErrorDialog("Drukowanie paragonu");
        receiptForm = new ReceiptForm();
        JTextArea textArea = receiptForm.getjTextArea1();
        textArea.append(String.format("%1$-10s%2$-5s%3$5s%4$10s\n","Name", "Qty","Price", "Value"));
        textArea.append("------------------------------\n");
        for (Map.Entry<String, OrderItem> entry : orderList.entrySet())         // wypisanie wszystkich elementów mapy produktów
        {
            OrderItem item = entry.getValue();
            BigDecimal value = new BigDecimal(item.getQuantity()).multiply(item.getProduct().getProductPrice());
            textArea.append(String.format("%1$-13s%2$2s%3$5s%4$10s\n", item.getProduct().getProductName(), item.getQuantity(),item.getProduct().getProductPrice(), value.toString()));
        }
        textArea.append("------------------------------\n");
        textArea.append(String.format("%1$20s%2$10s", "Total", total.toString())); //wyisanie należności
        
        receiptForm.setVisible(true);        
        try {
            receiptForm.getjTextArea1().print();
        } catch (PrinterException ex) {
            Logger.getLogger(PointOfSale.class.getName()).log(Level.SEVERE, null, ex);
        }
        errorForm.setVisible(false);
        errorForm = null;  
        showErrorDialog("Transakcja zakonczona!");
        resetData();                                                            // resetowanie POS      
    }
    
    /**
     * Klasa służąca do przywrócenia obiektu POS do stanu początkowego
     * Zerowana jest wartość Total, czyszczona mapa produktów oraz czyszczone jest 
     * okno POSForm, wyświetlające tabelę z produktami.
     */
    private void resetData()
    {
        total = new BigDecimal("0");
        orderList.clear();
        posWindow.reset();
    }
    
    public Map<String, OrderItem> getOrderList() {
        return orderList;
    }

    public POSForm getPosWindow() {
        return posWindow;
    }

    public DBMocker getDbMocker() {
        return dbMocker;
    }
    
    public void setErrorForm(ErrorForm errorForm) {
        this.errorForm = errorForm;
    }

    public ErrorForm getErrorForm() {
        return errorForm;
    }

    public ReceiptForm getReceiptForm() {
        return receiptForm;
    }

    public void setReceiptForm(ReceiptForm receiptForm) {
        this.receiptForm = receiptForm;
    }

    public void setOrderList(Map<String, OrderItem> orderList) {
        this.orderList = orderList;
    }

    public void setPosWindow(POSForm posWindow) {
        this.posWindow = posWindow;
    }

    public void setDbMocker(DBMocker dbMocker) {
        this.dbMocker = dbMocker;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
}
