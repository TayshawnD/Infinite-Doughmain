import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MenuPanel - Main menu interface for building orders
 * 
 * @author Infinite Doughmain Team
 * @version 2.0
 */
public class MenuPanel extends JPanel {
    private PizzaOrderingSystem app;
    
    // Pizza components
    private JComboBox<String> sizeCombo;
    private JComboBox<String> crustCombo;
    private JCheckBox[] toppingBoxes;
    private JButton addPizzaButton;
    
    // Beverage components
    private JComboBox<String> beverageCombo;
    private JComboBox<String> bevSizeCombo;
    private JSpinner bevQuantitySpinner;
    private JButton addBeverageButton;
    
    // Price display labels
    private JLabel sizePriceLabel;
    private JLabel crustPriceLabel;
    private JLabel pizzaPriceLabel;
    private JLabel bevPriceLabel;
    
    // Order display
    private JTextArea orderDisplay;
    private JLabel totalLabel;
    private JButton viewOrderButton;
    private JButton clearOrderButton;
    
    /**
     * Constructor
     * @param app Reference to main application
     */
    public MenuPanel(PizzaOrderingSystem app) {
        this.app = app;
        initializeComponents();
        layoutComponents();
    }
    
    /**
     * Initializes UI components
     */
    private void initializeComponents() {
        // Pizza size
        sizeCombo = new JComboBox<>(PizzaOrderingSystem.getPizzaSizes());
        sizeCombo.addActionListener(e -> updatePizzaPrice());
        
        // Crust type
        crustCombo = new JComboBox<>(PizzaOrderingSystem.getCrustTypes());
        crustCombo.addActionListener(e -> updatePizzaPrice());
        
        // Toppings (max 4)
        String[] toppings = PizzaOrderingSystem.getToppings();
        toppingBoxes = new JCheckBox[toppings.length];
        for (int i = 0; i < toppings.length; i++) {
            toppingBoxes[i] = new JCheckBox(toppings[i]);
            toppingBoxes[i].addActionListener(e -> {
                checkToppingLimit();
                updatePizzaPrice();
            });
        }
        
        addPizzaButton = new JButton("Add Pizza to Order");
        addPizzaButton.addActionListener(e -> addPizza());
        
        // Beverages
        beverageCombo = new JComboBox<>(PizzaOrderingSystem.getBeverages());
        bevSizeCombo = new JComboBox<>(PizzaOrderingSystem.getBevSizes());
        bevQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        
        addBeverageButton = new JButton("Add Beverage to Order");
        addBeverageButton.addActionListener(e -> addBeverage());
        
        // Order display
        orderDisplay = new JTextArea(10, 40);
        orderDisplay.setEditable(false);
        orderDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        viewOrderButton = new JButton("View Order Summary");
        viewOrderButton.addActionListener(e -> app.showOrderSummary());
        
        clearOrderButton = new JButton("Clear Order");
        clearOrderButton.addActionListener(e -> {
            app.clearOrder();
            updateOrderDisplay();
        });
    }
    
    /**
     * Lays out components in the panel
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top: Customer info
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Customer: " + app.getCurrentCustomer().getName()));
        add(topPanel, BorderLayout.NORTH);
        
        // Center: Menu options
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Left: Pizza builder
        JPanel pizzaPanel = new JPanel();
        pizzaPanel.setBorder(new TitledBorder("Build Your Pizza"));
        pizzaPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        pizzaPanel.add(new JLabel("Size:"), gbc);
        gbc.gridx = 1;
        pizzaPanel.add(sizeCombo, gbc);
        gbc.gridx = 2;
        sizePriceLabel = new JLabel();
        pizzaPanel.add(sizePriceLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        pizzaPanel.add(new JLabel("Crust:"), gbc);
        gbc.gridx = 1;
        pizzaPanel.add(crustCombo, gbc);
        gbc.gridx = 2;
        crustPriceLabel = new JLabel();
        pizzaPanel.add(crustPriceLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 3;
        pizzaPanel.add(new JLabel("Toppings (select up to 4):"), gbc);
        
        gbc.gridy = 3;
        JPanel toppingPanel = new JPanel(new GridLayout(4, 2));
        for (JCheckBox box : toppingBoxes) {
            toppingPanel.add(box);
        }
        pizzaPanel.add(toppingPanel, gbc);
        
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        pizzaPriceLabel = new JLabel("Pizza Price: $0.00");
        pizzaPriceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pizzaPanel.add(pizzaPriceLabel, gbc);
        
        gbc.gridy = 5;
        pizzaPanel.add(addPizzaButton, gbc);
        
        // Right: Beverages
        JPanel beveragePanel = new JPanel();
        beveragePanel.setBorder(new TitledBorder("Beverages"));
        beveragePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.WEST;
        
        gbc2.gridx = 0; gbc2.gridy = 0;
        beveragePanel.add(new JLabel("Beverage:"), gbc2);
        gbc2.gridx = 1;
        beveragePanel.add(beverageCombo, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 1;
        beveragePanel.add(new JLabel("Size:"), gbc2);
        gbc2.gridx = 1;
        beveragePanel.add(bevSizeCombo, gbc2);
        gbc2.gridx = 2;
        bevPriceLabel = new JLabel();
        beveragePanel.add(bevPriceLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 2;
        beveragePanel.add(new JLabel("Quantity:"), gbc2);
        gbc2.gridx = 1;
        beveragePanel.add(bevQuantitySpinner, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 3;
        gbc2.gridwidth = 3;
        beveragePanel.add(addBeverageButton, gbc2);
        
        centerPanel.add(pizzaPanel);
        centerPanel.add(beveragePanel);
        
        // Bottom: Order display
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new TitledBorder("Current Order"));
        bottomPanel.add(new JScrollPane(orderDisplay), BorderLayout.CENTER);
        
        JPanel bottomButtons = new JPanel(new FlowLayout());
        bottomButtons.add(totalLabel);
        bottomButtons.add(viewOrderButton);
        bottomButtons.add(clearOrderButton);
        bottomPanel.add(bottomButtons, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Initialize price displays
        updatePizzaPrice();
        updateBeveragePrice();
        bevSizeCombo.addActionListener(e -> updateBeveragePrice());
    }
    
    /**
     * Updates pizza price display
     */
    private void updatePizzaPrice() {
        int sizeIdx = sizeCombo.getSelectedIndex();
        int crustIdx = crustCombo.getSelectedIndex();
        
        double sizePrice = PizzaOrderingSystem.getSizePrices()[sizeIdx];
        double crustPrice = PizzaOrderingSystem.getCrustPrices()[crustIdx];
        
        int toppingCount = 0;
        for (JCheckBox box : toppingBoxes) {
            if (box.isSelected()) toppingCount++;
        }
        double toppingPrice = toppingCount * PizzaOrderingSystem.getToppingPrice();
        
        double total = sizePrice + crustPrice + toppingPrice;
        
        // Update labels
        if (sizePriceLabel != null) {
            sizePriceLabel.setText(String.format("$%.2f", sizePrice));
        }
        if (crustPriceLabel != null) {
            crustPriceLabel.setText(String.format("$%.2f", crustPrice));
        }
        if (pizzaPriceLabel != null) {
            pizzaPriceLabel.setText(String.format("Pizza Price: $%.2f", total));
        }
    }
    
    /**
     * Updates beverage price display
     */
    private void updateBeveragePrice() {
        int sizeIdx = bevSizeCombo.getSelectedIndex();
        double price = PizzaOrderingSystem.getBevPrices()[sizeIdx];
        
        if (bevPriceLabel != null) {
            bevPriceLabel.setText(String.format("$%.2f", price));
        }
    }
    
    /**
     * Enforces maximum of 4 toppings
     */
    private void checkToppingLimit() {
        int selected = 0;
        for (JCheckBox box : toppingBoxes) {
            if (box.isSelected()) selected++;
        }
        
        if (selected >= 4) {
            for (JCheckBox box : toppingBoxes) {
                if (!box.isSelected()) {
                    box.setEnabled(false);
                }
            }
        } else {
            for (JCheckBox box : toppingBoxes) {
                box.setEnabled(true);
            }
        }
    }
    
    /**
     * Adds pizza to order
     */
    private void addPizza() {
        String size = (String) sizeCombo.getSelectedItem();
        String crust = (String) crustCombo.getSelectedItem();
        
        List<String> selectedToppings = new ArrayList<>();
        for (JCheckBox box : toppingBoxes) {
            if (box.isSelected()) {
                selectedToppings.add(box.getText());
            }
        }
        
        // Build description
        StringBuilder desc = new StringBuilder(size + " " + crust + " Pizza");
        if (!selectedToppings.isEmpty()) {
            desc.append(" with ");
            desc.append(String.join(", ", selectedToppings));
        }
        
        // Calculate price
        int sizeIdx = sizeCombo.getSelectedIndex();
        int crustIdx = crustCombo.getSelectedIndex();
        double price = PizzaOrderingSystem.getSizePrices()[sizeIdx] + 
                      PizzaOrderingSystem.getCrustPrices()[crustIdx] + 
                      (selectedToppings.size() * PizzaOrderingSystem.getToppingPrice());
        
        OrderItem item = new OrderItem(desc.toString(), price, 1);
        app.addToOrder(item);
        
        // Reset form
        sizeCombo.setSelectedIndex(0);
        crustCombo.setSelectedIndex(0);
        for (JCheckBox box : toppingBoxes) {
            box.setSelected(false);
        }
        updatePizzaPrice();
    }
    
    /**
     * Adds beverage to order
     */
    private void addBeverage() {
        String beverage = (String) beverageCombo.getSelectedItem();
        String size = (String) bevSizeCombo.getSelectedItem();
        int quantity = (Integer) bevQuantitySpinner.getValue();
        
        int sizeIdx = bevSizeCombo.getSelectedIndex();
        double price = PizzaOrderingSystem.getBevPrices()[sizeIdx];
        
        String desc = size + " " + beverage;
        OrderItem item = new OrderItem(desc, price, quantity);
        app.addToOrder(item);
    }
    
    /**
     * Refreshes the panel (called when switching to this view)
     */
    public void refresh() {
        updateOrderDisplay();
    }
    
    /**
     * Updates the order display area
     */
    public void updateOrderDisplay() {
        StringBuilder sb = new StringBuilder();
        List<OrderItem> order = app.getCurrentOrder();
        
        if (order.isEmpty()) {
            sb.append("No items in order yet.\n");
        } else {
            for (int i = 0; i < order.size(); i++) {
                OrderItem item = order.get(i);
                sb.append(String.format("%d. %s\n", i + 1, item.toString()));
            }
        }
        
        orderDisplay.setText(sb.toString());
        totalLabel.setText(String.format("Total: $%.2f", app.calculateTotal()));
    }
}

