import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * LoginPanel - Handles customer login/registration
 * 
 * @author Infinite Doughmain Team
 * @version 2.0
 */
public class LoginPanel extends JPanel {
    private PizzaOrderingSystem app;
    private CustomerManager customerManager;
    
    private JTextField phoneField;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;
    private JButton loginButton;
    private JButton newCustomerButton;
    private JLabel statusLabel;
    
    /**
     * Constructor
     * @param app Reference to main application
     */
    public LoginPanel(PizzaOrderingSystem app) {
        this.app = app;
        this.customerManager = new CustomerManager();
        initializeComponents();
        layoutComponents();
    }
    
    /**
     * Initializes UI components
     */
    private void initializeComponents() {
        phoneField = new JTextField(15);
        nameField = new JTextField(20);
        addressField = new JTextField(20);
        cityField = new JTextField(15);
        stateField = new JTextField(5);
        zipField = new JTextField(10);
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        
        newCustomerButton = new JButton("New Customer");
        newCustomerButton.addActionListener(e -> handleNewCustomer());
        
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
    }
    
    /**
     * Lays out components in the panel
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        JLabel title = new JLabel("Infinite Doughmain - Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(title, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 1;
        
        // Phone field
        centerPanel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(phoneField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(addressField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(cityField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        centerPanel.add(new JLabel("State:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(stateField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        centerPanel.add(new JLabel("ZIP:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(zipField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(newCustomerButton);
        centerPanel.add(buttonPanel, gbc);
        
        gbc.gridy = 8;
        centerPanel.add(statusLabel, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Handles login attempt
     */
    private void handleLogin() {
        String phone = phoneField.getText().trim();
        if (phone.isEmpty()) {
            statusLabel.setText("Please enter a phone number");
            return;
        }
        
        Customer customer = customerManager.findCustomer(phone);
        if (customer != null) {
            app.showMenu(customer);
            statusLabel.setText(" ");
        } else {
            statusLabel.setText("Customer not found. Please register as new customer.");
        }
    }
    
    /**
     * Handles new customer registration
     */
    private void handleNewCustomer() {
        String phone = phoneField.getText().trim();
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String city = cityField.getText().trim();
        String state = stateField.getText().trim();
        String zip = zipField.getText().trim();
        
        if (phone.isEmpty() || name.isEmpty() || address.isEmpty() || 
            city.isEmpty() || state.isEmpty() || zip.isEmpty()) {
            statusLabel.setText("Please fill in all required fields");
            return;
        }
        
        Customer customer = new Customer(phone, name, address, city, state, zip);
        customerManager.saveCustomer(customer);
        app.showMenu(customer);
        statusLabel.setText(" ");
    }
}

