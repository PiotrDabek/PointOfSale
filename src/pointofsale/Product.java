package pointofsale;

import java.math.BigDecimal;

/**
 * 
 * Klasa reprezentująca produkt. 
 * Produkt posiada takie atrybuty jak nazwa, ID(kod kreskowy) i cena.
 * Cena produktu reprezentowana jest jako obiekt typu BigDecimal, która zdecydowanie 
 * lepiej nadaje się do rozliczeń finansowych niż np. typ float.
 */
public class Product {
    
    private String productName;                                                 // nazwa produktu
    private String productID;                                                   // ID produktu (kod kreskowy)
    private BigDecimal productPrice;                                            // Cena produktu jako BigDecimal

    public Product(String prodName, String prodID, String prodPrice) {
        this.productName = prodName;
        this.productID = prodID;
        this.productPrice = new BigDecimal(prodPrice);
    }

    public String getProductName() {
        return productName;
    }

    public String getProductID() {
        return productID;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    
    
}
