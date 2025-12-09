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
    private String subdivision;
    private String intersection;
    private String chargeAccountType; // Visa, MasterCard, etc.
    private String cardLast4; // Last 4 digits of card
    
    /**
     * Constructor
     * @param phone Customer phone number main identifier for the customer
     * @param name Customer full name
     * @param address Street address
     * @param city City
     * @param state State
     * @param zip ZIP code
     * @param subdivision Subdivision or complex name
     * @param intersection Closest major intersection
     * @param chargeAccountType Type of charge account (Visa, MasterCard, etc.)
     * @param cardLast4 Last 4 digits of credit card
     */
    public Customer(String phone, String name, String address, String city, String state, String zip,
                   String subdivision, String intersection, String chargeAccountType, String cardLast4) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.subdivision = subdivision != null ? subdivision : "";
        this.intersection = intersection != null ? intersection : "";
        this.chargeAccountType = chargeAccountType != null ? chargeAccountType : "";
        this.cardLast4 = cardLast4 != null ? cardLast4 : "";
    }
    
    // Getters for customer information
    public String getPhone() { return phone; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZip() { return zip; }
    public String getSubdivision() { return subdivision; }
    public String getIntersection() { return intersection; }
    public String getChargeAccountType() { return chargeAccountType; }
    public String getCardLast4() { return cardLast4; }
    
    /**
     * Returns formatted address string
     * @return Full address as string
     */
    public String getFullAddress() {
        return address + ", " + city + ", " + state + " " + zip;
    }
    
    /**
     * Returns delivery location information
     * @return Delivery location details including subdivision and intersection
     */
    public String getDeliveryInfo() {
        StringBuilder info = new StringBuilder();
        if (subdivision != null && !subdivision.isEmpty()) {
            info.append("Subdivision: ").append(subdivision);
        }
        if (intersection != null && !intersection.isEmpty()) {
            if (info.length() > 0) info.append(" | ");
            info.append("Intersection: ").append(intersection);
        }
        return info.length() > 0 ? info.toString() : "No additional location info";
    }
    
    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}

