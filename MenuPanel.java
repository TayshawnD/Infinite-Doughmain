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
    
    // Order type
    private JRadioButton pickupRadio;
    private JRadioButton deliveryRadio;
    private ButtonGroup orderTypeGroup;
    
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
        sizeCombo.setBackground(Color.WHITE);
        sizeCombo.addActionListener(e -> updatePizzaPrice());
        
        // Crust type
        crustCombo = new JComboBox<>(PizzaOrderingSystem.getCrustTypes());
        crustCombo.setBackground(Color.WHITE);
        crustCombo.addActionListener(e -> updatePizzaPrice());
        
        // Toppings (max 4)
        String[] toppings = PizzaOrderingSystem.getToppings();
        toppingBoxes = new JCheckBox[toppings.length];
        for (int i = 0; i < toppings.length; i++) {
            toppingBoxes[i] = new JCheckBox(toppings[i]);
            toppingBoxes[i].setBackground(Color.WHITE);
            toppingBoxes[i].setForeground(new Color(0, 100, 0)); // Dark green text
            toppingBoxes[i].addActionListener(e -> {
                checkToppingLimit();
                updatePizzaPrice();
            });
        }
        
        addPizzaButton = new JButton("Add Pizza to Order");
        addPizzaButton.setBackground(new Color(0, 150, 0)); // Green
        addPizzaButton.setForeground(Color.BLACK);
        addPizzaButton.setFont(new Font("Arial", Font.BOLD, 14));
        addPizzaButton.setFocusPainted(false);
        addPizzaButton.addActionListener(e -> addPizza());
        
        // Beverages
        beverageCombo = new JComboBox<>(PizzaOrderingSystem.getBeverages());
        beverageCombo.setBackground(Color.WHITE);
        bevSizeCombo = new JComboBox<>(PizzaOrderingSystem.getBevSizes());
        bevSizeCombo.setBackground(Color.WHITE);
        bevQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        ((JSpinner.DefaultEditor) bevQuantitySpinner.getEditor()).getTextField().setBackground(Color.WHITE);
        
        addBeverageButton = new JButton("Add Beverage to Order");
        addBeverageButton.setBackground(new Color(0, 150, 0)); // Green
        addBeverageButton.setForeground(Color.BLACK);
        addBeverageButton.setFont(new Font("Arial", Font.BOLD, 14));
        addBeverageButton.setFocusPainted(false);
        addBeverageButton.addActionListener(e -> addBeverage());
        
        // Order type selection
        pickupRadio = new JRadioButton("Pickup", true);
        pickupRadio.setBackground(Color.WHITE);
        pickupRadio.setForeground(new Color(0, 100, 0)); // Dark green
        pickupRadio.setFont(new Font("Arial", Font.BOLD, 12));
        pickupRadio.addActionListener(e -> app.setOrderType("Pickup"));
        
        deliveryRadio = new JRadioButton("Delivery");
        deliveryRadio.setBackground(Color.WHITE);
        deliveryRadio.setForeground(new Color(0, 100, 0)); // Dark green
        deliveryRadio.setFont(new Font("Arial", Font.BOLD, 12));
        deliveryRadio.addActionListener(e -> app.setOrderType("Delivery"));
        
        orderTypeGroup = new ButtonGroup();
        orderTypeGroup.add(pickupRadio);
        orderTypeGroup.add(deliveryRadio);
        
        // Set default to Pickup
        app.setOrderType("Pickup");
        
        // Order display
        orderDisplay = new JTextArea(10, 40);
        orderDisplay.setEditable(false);
        orderDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        orderDisplay.setBackground(new Color(255, 250, 250)); // Light white with slight red tint
        orderDisplay.setForeground(new Color(0, 80, 0)); // Dark green text
        
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(new Color(200, 0, 0)); // Red
        
        viewOrderButton = new JButton("View Order Summary");
        viewOrderButton.setBackground(new Color(0, 150, 0)); // Green
        viewOrderButton.setForeground(Color.BLACK);
        viewOrderButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewOrderButton.setFocusPainted(false);
        viewOrderButton.addActionListener(e -> app.showOrderSummary());
        
        clearOrderButton = new JButton("Clear Order");
        clearOrderButton.setBackground(new Color(200, 0, 0)); // Red
        clearOrderButton.setForeground(Color.BLACK);
        clearOrderButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearOrderButton.setFocusPainted(false);
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
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 0, 0), 3), // Red border
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Top: Customer info and order type
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerPanel.setBackground(Color.WHITE);
        Customer customer = app.getCurrentCustomer();
        JLabel customerLabel = new JLabel("Customer: " + (customer != null ? customer.getName() : ""));
        customerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customerLabel.setForeground(new Color(0, 100, 0)); // Dark green
        customerPanel.add(customerLabel);
        
        JPanel orderTypePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        orderTypePanel.setBackground(Color.WHITE);
        JLabel orderTypeLabel = new JLabel("Order Type: ");
        orderTypeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        orderTypeLabel.setForeground(new Color(200, 0, 0)); // Red
        orderTypePanel.add(orderTypeLabel);
        orderTypePanel.add(pickupRadio);
        orderTypePanel.add(deliveryRadio);
        
        topPanel.add(customerPanel, BorderLayout.WEST);
        topPanel.add(orderTypePanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        // Center: Menu options
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        
        // Left: Pizza builder
        JPanel pizzaPanel = new JPanel();
        pizzaPanel.setBackground(Color.WHITE);
        TitledBorder pizzaBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 150, 0), 2), // Green border
            "Build Your Pizza"
        );
        pizzaBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        pizzaBorder.setTitleColor(new Color(0, 100, 0)); // Dark green
        pizzaPanel.setBorder(pizzaBorder);
        pizzaPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        sizeLabel.setForeground(new Color(0, 100, 0)); // Dark green
        pizzaPanel.add(sizeLabel, gbc);
        gbc.gridx = 1;
        pizzaPanel.add(sizeCombo, gbc);
        gbc.gridx = 2;
        sizePriceLabel = new JLabel();
        sizePriceLabel.setForeground(new Color(200, 0, 0)); // Red
        sizePriceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        pizzaPanel.add(sizePriceLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel crustLabel = new JLabel("Crust:");
        crustLabel.setFont(new Font("Arial", Font.BOLD, 12));
        crustLabel.setForeground(new Color(0, 100, 0)); // Dark green
        pizzaPanel.add(crustLabel, gbc);
        gbc.gridx = 1;
        pizzaPanel.add(crustCombo, gbc);
        gbc.gridx = 2;
        crustPriceLabel = new JLabel();
        crustPriceLabel.setForeground(new Color(200, 0, 0)); // Red
        crustPriceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        pizzaPanel.add(crustPriceLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 3;
        JLabel toppingLabel = new JLabel("Toppings (select up to 4):");
        toppingLabel.setFont(new Font("Arial", Font.BOLD, 12));
        toppingLabel.setForeground(new Color(0, 100, 0)); // Dark green
        pizzaPanel.add(toppingLabel, gbc);
        
        gbc.gridy = 3;
        JPanel toppingPanel = new JPanel(new GridLayout(4, 2));
        toppingPanel.setBackground(Color.WHITE);
        for (JCheckBox box : toppingBoxes) {
            toppingPanel.add(box);
        }
        pizzaPanel.add(toppingPanel, gbc);
        
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        pizzaPriceLabel = new JLabel("Pizza Price: $0.00");
        pizzaPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        pizzaPriceLabel.setForeground(new Color(200, 0, 0)); // Red
        pizzaPanel.add(pizzaPriceLabel, gbc);
        
        gbc.gridy = 5;
        pizzaPanel.add(addPizzaButton, gbc);
        
        // Right: Beverages
        JPanel beveragePanel = new JPanel();
        beveragePanel.setBackground(Color.WHITE);
        TitledBorder bevBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 150, 0), 2), // Green border
            "Beverages"
        );
        bevBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        bevBorder.setTitleColor(new Color(0, 100, 0)); // Dark green
        beveragePanel.setBorder(bevBorder);
        beveragePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.WEST;
        
        gbc2.gridx = 0; gbc2.gridy = 0;
        JLabel bevLabel = new JLabel("Beverage:");
        bevLabel.setFont(new Font("Arial", Font.BOLD, 12));
        bevLabel.setForeground(new Color(0, 100, 0)); // Dark green
        beveragePanel.add(bevLabel, gbc2);
        gbc2.gridx = 1;
        beveragePanel.add(beverageCombo, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 1;
        JLabel bevSizeLabel = new JLabel("Size:");
        bevSizeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        bevSizeLabel.setForeground(new Color(0, 100, 0)); // Dark green
        beveragePanel.add(bevSizeLabel, gbc2);
        gbc2.gridx = 1;
        beveragePanel.add(bevSizeCombo, gbc2);
        gbc2.gridx = 2;
        bevPriceLabel = new JLabel();
        bevPriceLabel.setForeground(new Color(200, 0, 0)); // Red
        bevPriceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        beveragePanel.add(bevPriceLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 2;
        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Arial", Font.BOLD, 12));
        qtyLabel.setForeground(new Color(0, 100, 0)); // Dark green
        beveragePanel.add(qtyLabel, gbc2);
        gbc2.gridx = 1;
        beveragePanel.add(bevQuantitySpinner, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 3;
        gbc2.gridwidth = 3;
        beveragePanel.add(addBeverageButton, gbc2);
        
        centerPanel.add(pizzaPanel);
        centerPanel.add(beveragePanel);
        
        // Bottom: Order display
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        TitledBorder orderBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 0, 0), 2), // Red border
            "Current Order"
        );
        orderBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        orderBorder.setTitleColor(new Color(200, 0, 0)); // Red
        bottomPanel.setBorder(orderBorder);
        JScrollPane scrollPane = new JScrollPane(orderDisplay);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 0, 0), 1));
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomButtons = new JPanel(new FlowLayout());
        bottomButtons.setBackground(Color.WHITE);
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

