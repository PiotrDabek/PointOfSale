package pointofsale;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 *  Klasa udająca bazę danych. Dane reprezentowane są w formie mapy, gdzie jednym
 * z atrubutów jest ID produktu (kod kreskowy) a drugim obekt klasy Product
 * reprezentujący produkt. 
 * @see Product
 * 
 */
public class DBMocker {
    private Map<String, Product> dataMap;                                       // mapa przechowująca dane

    public DBMocker() {
        dataMap = new TreeMap<>();                                              // mapa jako TreeMap
        populate();                                                             // zapełnienie bazy rekordami
    }
    
    /**
     * Metoda służy do zapełnienia bazy rekordami. Kolejne produkty dodawane
     * są do mapy
     */
    public void populate()
    {         
        Product prod = new Product("latte", "123454", "7.23");
        dataMap.put(prod.getProductID(),prod);
        prod = new Product("czarna", "12333", "4.43");
        dataMap.put(prod.getProductID(),prod);
        prod = new Product("tost", "123464", "5.23");
        dataMap.put(prod.getProductID(),prod);
        prod = new Product("espresso", "napis", "1.33");
        dataMap.put(prod.getProductID(),prod);
        prod = new Product("doppio", "02938", "2.22");
        dataMap.put(prod.getProductID(),prod);

    }

    public Map<String, Product> getDataMap() {
        return dataMap;
    }

    /**
     * Metoda służąca do wydobywania rekodrów z udawanej bazy danych.
     * W formie przypomina odczyt z rzeczywistej bazy danych z użyciem DataSet.
     * Zwracany rekord przekazywany jest w formie mapy, której klucz jest nazwą 
     * koluny w tabeli. Drugi element mapy jest przekazywaną własnością produktu,
     * np. ["Name, Nazwa produktu]. 
     * W przypadku, gdy obiektu nie ma w bazie zwracany jest null
     * @param ID ID (kod kreskowy) produktu, którego szukamy
     * @return mapa nazw kolumn tabeli i własności produktów np. ["Name", Nazwa produktu]. lub null gdy obiektu nie ma w bazie danych
     * 
     */
    public Map<String, String> selectProduct(String ID)
    {
        if(dataMap.containsKey(ID))                                             // sprawdzenie czy produkt jest w bazie
        {
            
            Product tmp = dataMap.get(ID);                                      // pobranie produktu
            Map<String, String> productMap = new TreeMap<>();
            productMap.put("Name", tmp.getProductName());
            productMap.put("ID", tmp.getProductID());
            productMap.put("Price", tmp.getProductPrice().toString());          // tworzenie zwracanej mapy
            return productMap;  
        }
        else return null;      
    } 
    
}
