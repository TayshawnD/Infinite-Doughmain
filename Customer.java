import java.io.Serializable;

/**
 * Customer class - Represents a customer in the system
 * 
 * @author Infinite Doughmain Team
 * @version 2.0
 */
public class Customer implements Serializable {
    private String phone;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    
    /**
     * Constructor
     * @param phone Customer phone number (key identifier)
     * @param name Customer full name
     * @param address Street address
     * @param city City
     * @param state State
     * @param zip ZIP code
     */
    public Customer(String phone, String name, String address, String city, String state, String zip) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    
    // Getters
    public String getPhone() { return phone; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZip() { return zip; }
    
    /**
     * Returns formatted address string
     * @return Full address as string
     */
    public String getFullAddress() {
        return address + ", " + city + ", " + state + " " + zip;
    }
    
    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}

