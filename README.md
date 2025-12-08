# Infinite Doughmain - Pizza Ordering System
## SWE 3313 Sprint 2

A standalone desktop application for taking pizza orders with a complete restaurant menu interface.

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

## User Guide

### Login/Registration
- Enter phone number to login (if customer exists)
- Fill in all fields and click "New Customer" to register
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

## Default Test Accounts

No default accounts are pre-configured. Users must register as new customers.

## Notes

- Customer data is stored in `customers/customers.dat` file
- All prices are displayed in real-time as selections are made
- Tax is calculated at 8% on the order summary
- The application uses Java Swing for the GUI
- All source code includes Javadoc-style comments

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
└── customers/ (created at runtime)
    └── customers.dat
```

## Development Notes

- Built with Java Swing for cross-platform compatibility
- File-based storage simulates database functionality
- All menu items and prices based on "Mom and Pop's" menu structure
- Maximum 4 toppings per pizza enforced in UI
- Real-time price calculation for all menu items

