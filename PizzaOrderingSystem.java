import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Pizza Ordering System - Main Application
 * SWE 3313 Sprint 2
 * 
 * A desktop application for taking pizza orders with menu items,
 * customer management, and order processing.
 * 
 * @author Infinite Doughmain Team
 * @version 2.0
 */
public class PizzaOrderingSystem extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Menu data
    private static final String[] PIZZA_SIZES = {"Small", "Medium", "Large", "XL"};
    private static final double[] SIZE_PRICES = {9.99, 12.99, 15.99, 18.99};
    private static final String[] CRUST_TYPES = {"Hand-Tossed", "Thin Crust", "Deep Dish"};
    private static final double[] CRUST_PRICES = {0.00, 0.50, 1.50};
    private static final String[] TOPPINGS = {"Pepperoni", "Sausage", "Mushrooms", "Onions", 
                                              "Green Peppers", "Black Olives", "Bacon", "Extra Cheese"};
    private static final double TOPPING_PRICE = 1.25;
    private static final String[] BEVERAGES = {"Coke", "Sprite", "Fanta", "Root Beer", "Water"};
    private static final String[] BEV_SIZES = {"Small (16oz)", "Medium (20oz)", "Large (2L)"};
    private static final double[] BEV_PRICES = {2.49, 2.99, 3.49};
    
    // Current order
    private java.util.List<OrderItem> currentOrder;
    private Customer currentCustomer;
    
    // Components
    private LoginPanel loginPanel;
    private MenuPanel menuPanel;
    private OrderSummaryPanel orderSummaryPanel;
    
    /**
     * Constructor - Initializes the application window
     */
    public PizzaOrderingSystem() {
        currentOrder = new ArrayList<>();
        setTitle("Infinite Doughmain - Pizza Ordering System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        // Set frame background to white
        getContentPane().setBackground(Color.WHITE);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        
        // Initialize panels
        loginPanel = new LoginPanel(this);
        menuPanel = new MenuPanel(this);
        orderSummaryPanel = new OrderSummaryPanel(this);
        
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(orderSummaryPanel, "SUMMARY");
        
        add(mainPanel);
        showLogin();
    }
    
    /**
     * Shows the login panel
     */
    public void showLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    /**
     * Shows the menu panel after successful login
     * @param customer The logged-in customer
     */
    public void showMenu(Customer customer) {
        currentCustomer = customer;
        menuPanel.refresh();
        cardLayout.show(mainPanel, "MENU");
    }
    
    /**
     * Shows the order summary panel
     */
    public void showOrderSummary() {
        orderSummaryPanel.refresh();
        cardLayout.show(mainPanel, "SUMMARY");
    }
    
    /**
     * Adds an item to the current order
     * @param item The order item to add
     */
    public void addToOrder(OrderItem item) {
        currentOrder.add(item);
        menuPanel.updateOrderDisplay();
    }
    
    /**
     * Gets the current order list
     * @return List of order items
     */
    public java.util.List<OrderItem> getCurrentOrder() {
        return currentOrder;
    }
    
    /**
     * Gets the current customer
     * @return Current customer object
     */
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }
    
    /**
     * Clears the current order
     */
    public void clearOrder() {
        currentOrder.clear();
        menuPanel.updateOrderDisplay();
    }
    
    /**
     * Calculates the total price of the current order
     * @return Total price as double
     */
    public double calculateTotal() {
        double total = 0.0;
        for (OrderItem item : currentOrder) {
            total += item.getPrice();
        }
        return total;
    }
    
    /**
     * Main entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PizzaOrderingSystem().setVisible(true);
        });
    }
    
    // Static getters for menu data
    public static String[] getPizzaSizes() { return PIZZA_SIZES; }
    public static double[] getSizePrices() { return SIZE_PRICES; }
    public static String[] getCrustTypes() { return CRUST_TYPES; }
    public static double[] getCrustPrices() { return CRUST_PRICES; }
    public static String[] getToppings() { return TOPPINGS; }
    public static double getToppingPrice() { return TOPPING_PRICE; }
    public static String[] getBeverages() { return BEVERAGES; }
    public static String[] getBevSizes() { return BEV_SIZES; }
    public static double[] getBevPrices() { return BEV_PRICES; }
}

