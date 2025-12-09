/**
 * OrderItem class - Represents an item in an order
 * 
 * @author Infinite Doughmain Team
 * @version 2.0
 */
public class OrderItem {
    private String description;
    private double price;
    private int quantity;
    
    /**
     * Constructor
     * @param description Item description
     * @param price Item price
     * @param quantity Item quantity
     */
    public OrderItem(String description, double price, int quantity) {
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
    
    /**
     * Gets the total price for this item (price * quantity)
     * @return Total price
     */
    public double getPrice() {
        return price * quantity;
    }
    
    /**
     * Gets the unit price
     * @return Unit price
     */
    public double getUnitPrice() {
        return price;
    }
    
    // Getters
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    
    @Override
    public String toString() {
        return String.format("%s x%d - $%.2f", description, quantity, getPrice());
    }
}

