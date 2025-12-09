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
    private JButton processPaymentButton;
    private JPanel paymentPanel;
    private JRadioButton cashRadio;
    private JRadioButton checkRadio;
    private JRadioButton creditRadio;
    private ButtonGroup paymentGroup;
    private JTextField paymentAmountField;
    private boolean paymentProcessed;
    
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
        
        // Payment selection
        cashRadio = new JRadioButton("Cash", true);
        cashRadio.setBackground(Color.WHITE);
        cashRadio.setForeground(new Color(0, 100, 0)); // Dark green
        cashRadio.setFont(new Font("Arial", Font.BOLD, 12));
        
        checkRadio = new JRadioButton("Check");
        checkRadio.setBackground(Color.WHITE);
        checkRadio.setForeground(new Color(0, 100, 0)); // Dark green
        checkRadio.setFont(new Font("Arial", Font.BOLD, 12));
        
        creditRadio = new JRadioButton("Credit Card");
        creditRadio.setBackground(Color.WHITE);
        creditRadio.setForeground(new Color(0, 100, 0)); // Dark green
        creditRadio.setFont(new Font("Arial", Font.BOLD, 12));
        
        paymentGroup = new ButtonGroup();
        paymentGroup.add(cashRadio);
        paymentGroup.add(checkRadio);
        paymentGroup.add(creditRadio);
        
        paymentAmountField = new JTextField(10);
        paymentAmountField.setBackground(new Color(255, 250, 250));
        
        processPaymentButton = new JButton("Process Payment");
        processPaymentButton.setBackground(new Color(0, 150, 0)); // Green
        processPaymentButton.setForeground(Color.BLACK);
        processPaymentButton.setFont(new Font("Arial", Font.BOLD, 14));
        processPaymentButton.setFocusPainted(false);
        processPaymentButton.addActionListener(e -> processPayment());
        
        paymentProcessed = false;
    }
    
    /**
     * Processes the payment and updates the receipt
     */
    private void processPayment() {
        String paymentType = "Cash";
        if (cashRadio.isSelected()) paymentType = "Cash";
        else if (checkRadio.isSelected()) paymentType = "Check";
        else if (creditRadio.isSelected()) paymentType = "Credit";
        
        double amount = 0.0;
        try {
            String amountText = paymentAmountField.getText().trim();
            if (!amountText.isEmpty()) {
                amount = Double.parseDouble(amountText);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid payment amount", 
                "Invalid Amount", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        app.setPaymentInfo(paymentType, amount);
        paymentProcessed = true;
        refresh();
        
        // Hide payment panel after processing
        if (paymentPanel != null) {
            paymentPanel.setVisible(false);
        }
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
        
        // Payment panel
        paymentPanel = new JPanel();
        paymentPanel.setBackground(Color.WHITE);
        paymentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 0, 0), 2),
            "Payment Information"
        ));
        paymentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel paymentLabel = new JLabel("Payment Method:");
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 12));
        paymentLabel.setForeground(new Color(0, 100, 0)); // Dark green
        paymentPanel.add(paymentLabel, gbc);
        
        gbc.gridx = 1;
        JPanel paymentMethodPanel = new JPanel(new FlowLayout());
        paymentMethodPanel.setBackground(Color.WHITE);
        paymentMethodPanel.add(cashRadio);
        paymentMethodPanel.add(checkRadio);
        paymentMethodPanel.add(creditRadio);
        paymentPanel.add(paymentMethodPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel amountLabel = new JLabel("Amount Tendered:");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 12));
        amountLabel.setForeground(new Color(0, 100, 0)); // Dark green
        paymentPanel.add(amountLabel, gbc);
        
        gbc.gridx = 1;
        paymentPanel.add(paymentAmountField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        paymentPanel.add(processPaymentButton, gbc);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(paymentPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(printButton);
        buttonPanel.add(newOrderButton);
        buttonPanel.add(backButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Refreshes the order summary display
     */
    public void refresh() {
        paymentProcessed = false; // Reset payment status when refreshing
        if (paymentPanel != null) {
            paymentPanel.setVisible(true);
        }
        
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        
        // Header
        sb.append("=".repeat(60)).append("\n");
        sb.append("          INFINITE DOUGHMAIN PIZZA\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append("Order Date: ").append(sdf.format(new Date())).append("\n");
        sb.append("Order Type: ").append(app.getOrderType().toUpperCase()).append("\n");
        Customer customer = app.getCurrentCustomer();
        if (customer != null) {
            sb.append("Customer: ").append(customer.getName()).append("\n");
            sb.append("Phone: ").append(customer.getPhone()).append("\n");
            sb.append("Address: ").append(customer.getFullAddress()).append("\n");
            if (app.getOrderType().equals("Delivery")) {
                String deliveryInfo = customer.getDeliveryInfo();
                if (deliveryInfo != null && !deliveryInfo.isEmpty() && !deliveryInfo.equals("No additional location info")) {
                    sb.append("Delivery Info: ").append(deliveryInfo).append("\n");
                }
            }
        }
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
        
        // Payment information
        if (paymentProcessed) {
            String paymentType = app.getPaymentType();
            double paymentAmount = app.getPaymentAmount();
            
            sb.append("Payment Method: ").append(paymentType).append("\n");
            if (paymentAmount > 0) {
                sb.append("Amount Tendered: $").append(String.format("%.2f", paymentAmount)).append("\n");
                double change = paymentAmount - total;
                if (change > 0) {
                    sb.append("Change: $").append(String.format("%.2f", change)).append("\n");
                }
            }
            sb.append("\n");
            
            // Signature line for credit card
            if (paymentType.equals("Credit")) {
                sb.append("-".repeat(60)).append("\n");
                sb.append("Signature: _________________________________\n");
                sb.append("-".repeat(60)).append("\n");
            }
        }
        
        sb.append("\nThank you for your order!\n");
        
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

