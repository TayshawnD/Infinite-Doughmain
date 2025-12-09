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
        
        // Style text fields
        Color fieldBg = new Color(255, 250, 250); // Light white with slight red tint
        phoneField.setBackground(fieldBg);
        nameField.setBackground(fieldBg);
        addressField.setBackground(fieldBg);
        cityField.setBackground(fieldBg);
        stateField.setBackground(fieldBg);
        zipField.setBackground(fieldBg);
        
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 150, 0)); // Green
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());
        
        newCustomerButton = new JButton("New Customer");
        newCustomerButton.setBackground(new Color(0, 150, 0)); // Green
        newCustomerButton.setForeground(Color.BLACK);
        newCustomerButton.setFont(new Font("Arial", Font.BOLD, 14));
        newCustomerButton.setFocusPainted(false);
        newCustomerButton.addActionListener(e -> handleNewCustomer());
        
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(new Color(200, 0, 0)); // Red
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
    }
    
    /**
     * Lays out components in the panel
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 0, 0), 3), // Red border
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        JLabel title = new JLabel("Infinite Doughmain - Login");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(200, 0, 0)); // Red
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(title, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 1;
        
        // Phone field
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 12));
        phoneLabel.setForeground(new Color(0, 100, 0)); // Dark green
        centerPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(phoneField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        nameLabel.setForeground(new Color(0, 100, 0)); // Dark green
        centerPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel addrLabel = new JLabel("Address:");
        addrLabel.setFont(new Font("Arial", Font.BOLD, 12));
        addrLabel.setForeground(new Color(0, 100, 0)); // Dark green
        centerPanel.add(addrLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(addressField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cityLabel.setForeground(new Color(0, 100, 0)); // Dark green
        centerPanel.add(cityLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(cityField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel stateLabel = new JLabel("State:");
        stateLabel.setFont(new Font("Arial", Font.BOLD, 12));
        stateLabel.setForeground(new Color(0, 100, 0)); // Dark green
        centerPanel.add(stateLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(stateField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel zipLabel = new JLabel("ZIP:");
        zipLabel.setFont(new Font("Arial", Font.BOLD, 12));
        zipLabel.setForeground(new Color(0, 100, 0)); // Dark green
        centerPanel.add(zipLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(zipField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
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

