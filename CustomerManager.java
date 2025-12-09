import java.io.*;
import java.util.*;

/**
 * CustomerManager - Handles customer data persistence using file storage
 * Simulates database functionality for standalone desktop application
 * 
 * @author Infinite Doughmain Team
 * @version 2.0
 */
public class CustomerManager {
    private static final String CUSTOMER_DIR = "customers";
    private static final String CUSTOMER_FILE = CUSTOMER_DIR + File.separator + "customers.dat";
    
    private Map<String, Customer> customers;
    
    /**
     * Constructor - Loads existing customer data
     */
    public CustomerManager() {
        customers = new HashMap<>();
        loadCustomers();
    }
    
    /**
     * Loads customers from file storage
     */
    @SuppressWarnings("unchecked")
    private void loadCustomers() {
        try {
            File dir = new File(CUSTOMER_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            File file = new File(CUSTOMER_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    customers = (Map<String, Customer>) ois.readObject();
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading customers: " + e.getMessage());
            customers = new HashMap<>();
        }
    }
    
    /**
     * Saves customers to file storage
     */
    private void saveCustomers() {
        try {
            File dir = new File(CUSTOMER_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CUSTOMER_FILE))) {
                oos.writeObject(customers);
            }
        } catch (Exception e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }
    
    /**
     * Finds a customer by phone number
     * @param phone Phone number to search for
     * @return Customer object or null if not found
     */
    public Customer findCustomer(String phone) {
        return customers.get(phone.replaceAll("[^0-9]", ""));
    }
    
    /**
     * Adds or updates a customer
     * @param customer Customer to save
     */
    public void saveCustomer(Customer customer) {
        String phone = customer.getPhone().replaceAll("[^0-9]", "");
        customers.put(phone, customer);
        saveCustomers();
    }
    
    /**
     * Checks if a customer exists
     * @param phone Phone number to check
     * @return True if customer exists
     */
    public boolean customerExists(String phone) {
        return customers.containsKey(phone.replaceAll("[^0-9]", ""));
    }
}

