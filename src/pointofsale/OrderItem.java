package pointofsale;

import java.math.BigDecimal;

/**
 *
 * Klasa reprezentująca element rachunku zawierający sam produkt jak i jego ilość
 */
public class OrderItem {
    
    private final Product product;
    private int quantity;

    public OrderItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Metoda służąca do wyliczenia należności za produkt wyliczonej jako iloraz 
     * ceny produktu i jego ilości
     * @return należność za produkt w danej ilości
     */
    public BigDecimal getTotal()
    {
        return product.getProductPrice().multiply(new BigDecimal(quantity));
    }
    
    /**
     * Klasa służąca do zwiększenia ilości danego produktu o 1
     */
    public void addQuantity()
    {
        quantity++;
    }
    
    
}
