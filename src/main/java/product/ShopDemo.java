package product;

import category.Category;

public class ShopDemo {
    public static void main(String[] args) {
        System.out.println("--- Lab 2: Encapsulation & Validation Guard Demo ---");

        // 1. Create a Category and a Product
        Category stationery = new Category("STAT-01", "Stationery", "Writing and drawing supplies");
        Product pen = new Product("PEN-001", "Ballpoint Pen", "A simple blue ballpoint pen", 1.50, 10, stationery);

        System.out.println("\n--- Initial Product Info (Pen) ---");
        pen.displayProductInfo();

        // 2. Accepted updates
        System.out.println("\n--- Demonstrating Accepted Updates ---");
        System.out.println("Attempting to set price to 2.50...");
        if (pen.trySetPrice(2.50)) {
            System.out.println("Price updated successfully. New price: " + pen.getPrice());
        } else {
            System.out.println("Failed to update price.");
        }
        pen.displayProductInfo();

        System.out.println("\nAttempting to add 20 units to stock (current: " + pen.getQuantity() + ")...");
        if (pen.addStock(20)) {
            System.out.println("Stock added successfully. New quantity: " + pen.getQuantity());
        } else {
            System.out.println("Failed to add stock.");
        }
        System.out.println("Stock Status: " + pen.getStockStatus());
        pen.displayProductInfo();

        // 3. Rejected updates
        System.out.println("\n--- Demonstrating Rejected Updates ---");
        System.out.println("Attempting to set price to -1.0...");
        if (pen.trySetPrice(-1.0)) {
            System.out.println("Price updated (ERROR: should not happen). New price: " + pen.getPrice());
        } else {
            System.out.println("Price update rejected as expected. Price remains: " + pen.getPrice());
        }

        System.out.println("\nAttempting to set name to 'A'...");
        if (pen.trySetName("A")) {
            System.out.println("Name updated (ERROR: should not happen). New name: " + pen.getName());
        } else {
            System.out.println("Name update rejected as expected. Name remains: " + pen.getName());
        }

        System.out.println("\nAttempting to sell 10000 units (current: " + pen.getQuantity() + ")...");
        if (pen.sellProduct(10000)) {
            System.out.println("Product sold (ERROR: should not happen). New quantity: " + pen.getQuantity());
        } else {
            System.out.println("Sale rejected as expected. Quantity remains: " + pen.getQuantity());
        }

        System.out.println("\nAttempting to apply 200% discount...");
        if (pen.applyDiscount(200)) {
            System.out.println("Discount applied (ERROR: should not happen). New price: " + pen.getPrice());
        } else {
            System.out.println("Discount rejected as expected. Price remains: " + pen.getPrice());
        }
        pen.displayProductInfo();


        // 4. Show getStockStatus() before/after restocks/sales (demonstrated above)
        // Additional demonstration for LOW stock
        System.out.println("\n--- Demonstrating LOW Stock Status ---");
        pen.trySetQuantity(5); // Set quantity to trigger LOW status
        System.out.println("Quantity set to 5. Stock Status: " + pen.getStockStatus());
        pen.displayProductInfo();

        pen.trySetQuantity(0); // Set quantity to trigger OUT_OF_STOCK status
        System.out.println("Quantity set to 0. Stock Status: " + pen.getStockStatus());
        pen.displayProductInfo();


        // 5. Add the product to the category twice; the second add must be rejected.
        System.out.println("\n--- Demonstrating Category.addProduct Safety ---");
        System.out.println("Attempting to add 'Pen' to 'Stationery' category for the first time...");
        if (stationery.addProduct(pen)) {
            System.out.println("'Pen' added to 'Stationery' successfully.");
        } else {
            System.out.println("Failed to add 'Pen' to 'Stationery'. (ERROR: should not happen)");
        }

        System.out.println("Attempting to add 'Pen' to 'Stationery' category for the second time (duplicate)...");
        if (stationery.addProduct(pen)) {
            System.out.println("'Pen' added to 'Stationery' successfully. (ERROR: should not happen)");
        } else {
            System.out.println("Duplicate 'Pen' rejected by 'Stationery' category as expected.");
        }

        Product nullProduct = null;
        System.out.println("Attempting to add a null product to 'Stationery' category...");
        if (stationery.addProduct(nullProduct)) {
            System.out.println("Null product added to 'Stationery' successfully. (ERROR: should not happen)");
        } else {
            System.out.println("Null product rejected by 'Stationery' category as expected.");
        }

        System.out.println("\n--- Final Category Info ---");
        stationery.displayCategoryInfo();

        System.out.println("\nDemo complete.");
    }
}
