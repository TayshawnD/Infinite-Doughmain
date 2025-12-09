# Pizza Ordering System
## SWE 3313 Sprint 2

A standalone desktop application for taking pizza orders with a complete restaurant menu interface, built using Java Swing.

## Features

- **Customer Management**: Login/Registration system with file-based storage
- **Complete Menu System**:
  - 4 Pizza Sizes (Small, Medium, Large, XL)
  - 8 Toppings (Pepperoni, Sausage, Mushrooms, Onions, Green Peppers, Black Olives, Bacon, Extra Cheese)
  - Up to 4 toppings per pizza
  - 3 Crust Options (Hand-Tossed, Thin Crust, Deep Dish)
  - 5 Beverages (Coke, Sprite, Fanta, Root Beer, Water)
  - 3 Beverage Sizes (Small 16oz, Medium 20oz, Large 2L)
- **Order Display**: Real-time order summary with itemized pricing
- **Order Summary**: Complete receipt view with totals, tax calculation, and print functionality

## Requirements

- Java JDK 8 or higher
- Java Swing (included with JDK)

## Building the Project

### Compile all Java files:
```bash
javac *.java
```

### Create executable JAR:
```bash
jar cvfe PizzaOrderingSystem.jar PizzaOrderingSystem *.class
```

### Run the application:
```bash
java -jar PizzaOrderingSystem.jar
```

Or run directly:
```bash
java PizzaOrderingSystem
```

**Note:** On Windows, you can use `build.bat` and on Linux/Mac, use `build.sh` to automatically compile and create the JAR file.

## User Guide

### Login/Registration

**Important:** This system uses phone numbers as the customer identifier, not traditional username/password combinations.

- **To Login:** Enter a phone number that has been previously registered
- **To Register:** Fill in all fields (phone number, name, address, city, state, ZIP) and click "New Customer"
- Phone number serves as the unique customer identifier

### Building an Order

**Pizza:**
1. Select pizza size (prices displayed automatically)
2. Choose crust type (prices displayed)
3. Select up to 4 toppings (checkboxes)
4. Click "Add Pizza to Order"

**Beverages:**
1. Select beverage type
2. Choose size
3. Set quantity
4. Click "Add Beverage to Order"

### Viewing Order
- Current order displays in bottom panel with running total
- Click "View Order Summary" to see complete receipt
- Click "Print Receipt" to print order summary

### Starting New Order
- Click "Start New Order" to clear current order and begin fresh
- Click "Back to Menu" to return to menu without clearing order

## File Structure

- `PizzaOrderingSystem.java` - Main application class
- `Customer.java` - Customer data model
- `OrderItem.java` - Order item data model
- `CustomerManager.java` - File-based customer storage
- `LoginPanel.java` - Login/Registration interface
- `MenuPanel.java` - Menu and order building interface
- `OrderSummaryPanel.java` - Order summary and receipt display
- `customers/` - Directory created automatically for customer data storage

## Test Accounts / Login Information

**No default accounts are pre-configured.** Users must register as new customers.

**Login Method:**
- The system uses phone numbers for customer identification
- To test the system:
  1. Click "New Customer" and fill in all required fields
  2. Use any phone number format (e.g., "555-1234", "(555) 123-4567", "5551234567")
  3. After registration, you can login using the same phone number

**Example Test Customer:**
- Phone: `555-1234` (or any format)
- Name: `John Doe`
- Address: `123 Main St`
- City: `Kennesaw`
- State: `GA`
- ZIP: `30144`

After creating this customer, you can login by entering `555-1234` (or the same phone number in any format) in the phone field and clicking "Login".

## Special Remarks

- Customer data is stored in `customers/customers.dat` file (created automatically)
- All prices are displayed in real-time as selections are made
- Tax is calculated at 8% on the order summary
- The application uses Java Swing for the GUI
- All source code includes Javadoc-style comments
- Maximum 4 toppings per pizza enforced in UI
- Phone numbers are normalized (non-numeric characters removed) for storage and lookup

## Menu Pricing (Mom and Pop's Menu)

**Pizza Sizes:**
- Small: $9.99
- Medium: $12.99
- Large: $15.99
- XL: $18.99

**Crust Options:**
- Hand-Tossed: $0.00 (included)
- Thin Crust: +$0.50
- Deep Dish: +$1.50

**Toppings:**
- Each topping: +$1.25
- Maximum 4 toppings per pizza

**Beverages:**
- Small (16oz): $2.49
- Medium (20oz): $2.99
- Large (2L): $3.49

## Project Structure

```
PizzaOrderingSystem/
├── PizzaOrderingSystem.java
├── Customer.java
├── OrderItem.java
├── CustomerManager.java
├── LoginPanel.java
├── MenuPanel.java
├── OrderSummaryPanel.java
├── README.md
├── build.bat (Windows build script)
├── build.sh (Linux/Mac build script)
└── customers/ (created at runtime)
    └── customers.dat
```

## Development Notes

- Built with Java Swing for cross-platform compatibility
- File-based storage simulates database functionality
- All menu items and prices based on "Mom and Pop's" menu structure
- Maximum 4 toppings per pizza enforced in UI
- Real-time price calculation for all menu items
