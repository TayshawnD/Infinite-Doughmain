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
        
        printButton = new JButton("Print Receipt");
        printButton.addActionListener(e -> printReceipt());
        
        newOrderButton = new JButton("Start New Order");
        newOrderButton.addActionListener(e -> {
            app.clearOrder();
            app.showMenu(app.getCurrentCustomer());
        });
        
        backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> app.showMenu(app.getCurrentCustomer()));
    }
    
    /**
     * Lays out components in the panel
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Order Summary"));
        topPanel.add(new JLabel("Customer: " + app.getCurrentCustomer().getName()));
        add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new TitledBorder("Order Details"));
        centerPanel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
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

