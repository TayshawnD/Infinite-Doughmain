import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * OrderSummaryPanel - Displays completed order with items, prices, and totals
 * 
 * @author Infinite Doughmain Team
 * @version 2.0
 */
public class OrderSummaryPanel extends JPanel {
    private PizzaOrderingSystem app;
    private JTextArea summaryArea;
    private JButton printButton;
    private JButton newOrderButton;
    private JButton backButton;
    
    /**
     * Constructor
     * @param app Reference to main application
     */
    public OrderSummaryPanel(PizzaOrderingSystem app) {
        this.app = app;
        initializeComponents();
        layoutComponents();
    }
    
    /**
     * Initializes UI components
     */
    private void initializeComponents() {
        summaryArea = new JTextArea(20, 50);
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        summaryArea.setBackground(new Color(255, 250, 250)); // Light white with slight red tint
        summaryArea.setForeground(new Color(0, 80, 0)); // Dark green text
        
        printButton = new JButton("Print Receipt");
        printButton.setBackground(new Color(0, 150, 0)); // Green
        printButton.setForeground(Color.BLACK);
        printButton.setFont(new Font("Arial", Font.BOLD, 14));
        printButton.setFocusPainted(false);
        printButton.addActionListener(e -> printReceipt());
        
        newOrderButton = new JButton("Start New Order");
        newOrderButton.setBackground(new Color(0, 150, 0)); // Green
        newOrderButton.setForeground(Color.BLACK);
        newOrderButton.setFont(new Font("Arial", Font.BOLD, 14));
        newOrderButton.setFocusPainted(false);
        newOrderButton.addActionListener(e -> {
            app.clearOrder();
            app.showMenu(app.getCurrentCustomer());
        });
        
        backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(200, 0, 0)); // Red
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> app.showMenu(app.getCurrentCustomer()));
    }
    
    /**
     * Lays out components in the panel
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 0, 0), 3), // Red border
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        JLabel summaryLabel = new JLabel("Order Summary");
        summaryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        summaryLabel.setForeground(new Color(200, 0, 0)); // Red
        topPanel.add(summaryLabel);
        Customer customer = app.getCurrentCustomer();
        JLabel customerLabel = new JLabel("Customer: " + (customer != null ? customer.getName() : ""));
        customerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customerLabel.setForeground(new Color(0, 100, 0)); // Dark green
        topPanel.add(customerLabel);
        add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        TitledBorder detailsBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 150, 0), 2), // Green border
            "Order Details"
        );
        detailsBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        detailsBorder.setTitleColor(new Color(0, 100, 0)); // Dark green
        centerPanel.setBorder(detailsBorder);
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(printButton);
        bottomPanel.add(newOrderButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Refreshes the order summary display
     */
    public void refresh() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        
        // Header
        sb.append("=".repeat(60)).append("\n");
        sb.append("          INFINITE DOUGHMAIN PIZZA\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append("Order Date: ").append(sdf.format(new Date())).append("\n");
        sb.append("Customer: ").append(app.getCurrentCustomer().getName()).append("\n");
        sb.append("Phone: ").append(app.getCurrentCustomer().getPhone()).append("\n");
        sb.append("Address: ").append(app.getCurrentCustomer().getFullAddress()).append("\n");
        sb.append("-".repeat(60)).append("\n\n");
        
        // Items
        sb.append(String.format("%-40s %6s %10s\n", "Item", "Qty", "Price"));
        sb.append("-".repeat(60)).append("\n");
        
        java.util.List<OrderItem> order = app.getCurrentOrder();
        double subtotal = 0.0;
        
        for (OrderItem item : order) {
            String desc = item.getDescription();
            if (desc.length() > 40) {
                desc = desc.substring(0, 37) + "...";
            }
            sb.append(String.format("%-40s %6d $%9.2f\n", 
                desc, item.getQuantity(), item.getPrice()));
            subtotal += item.getPrice();
        }
        
        sb.append("-".repeat(60)).append("\n");
        sb.append(String.format("%-40s %6s $%9.2f\n", "Subtotal", "", subtotal));
        
        double tax = subtotal * 0.08; // 8% tax
        sb.append(String.format("%-40s %6s $%9.2f\n", "Tax (8%)", "", tax));
        
        double total = subtotal + tax;
        sb.append("=".repeat(60)).append("\n");
        sb.append(String.format("%-40s %6s $%9.2f\n", "TOTAL", "", total));
        sb.append("=".repeat(60)).append("\n\n");
        
        sb.append("Thank you for your order!\n");
        
        summaryArea.setText(sb.toString());
    }
    
    /**
     * Prints the receipt (opens print dialog)
     */
    private void printReceipt() {
        try {
            summaryArea.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error printing: " + e.getMessage(), 
                "Print Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

