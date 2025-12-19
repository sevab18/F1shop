package product;

import category.Category;
import product.pricing.PricePolicy;

import java.util.List;

public class Product {
    public static final String DEFAULT_CURRENCY = "KZT";

    private static int SEQ = 1;
    private static int createdCount = 0;

    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Category category;
    // stock status is derived from quantity, not a field

    // Constructors with chaining
    public Product() {
        this("AUTO-" + nextSeq(), "Unnamed", null, 0.0, 0, null);
    }

    public Product(String id, String name, double price) {
        this(id, name, null, price, 0, null);
    }

    public Product(String id, String name, String description, double price, int quantity, Category category) {
        // Initialize with defaults or nulls first, then try to set with provided values
        this.id = "AUTO-" + nextSeq();
        this.name = "Unnamed";
        this.description = null;
        this.price = 0.0;
        this.quantity = 0;
        this.category = null;

        trySetId(id);
        trySetName(name);
        trySetDescription(description);
        trySetPrice(price);
        trySetQuantity(quantity);
        trySetCategory(category);
        createdCount++;
    }

    public static Product of(String id, String name, double price) {
        return new Product(id, name, price);
    }

    public static Product freeSample(String name) {
        Product p = new Product();
        p.trySetName(name);
        p.trySetPrice(0.0);
        p.trySetQuantity(1);
        return p;
    }

    private static String nextSeq() {
        return String.valueOf(SEQ++);
    }

    public static int getCreatedCount() {
        return createdCount;
    }

    public boolean addStock(int amount) {
        if (amount > 0) {
            return trySetQuantity(this.quantity + amount);
        }
        return false;
    }

    public boolean sellProduct(int amount) {
        if (amount > 0 && amount <= this.quantity) {
            return trySetQuantity(this.quantity - amount);
        }
        return false;
    }

    public boolean applyDiscount(double percent) {
        if (percent >= 0 && percent <= 90) { // Discount percentage must be 0..90
            double newPrice = this.price * (1 - percent / 100.0);
            return trySetPrice(newPrice);
        }
        return false;
    }

    public double calculateTotalValue() {
        return this.price * this.quantity;
    }

    public String getStockStatus() {
        if (quantity == 0) {
            return "OUT_OF_STOCK";
        } else if (quantity > 0 && quantity <= 10) {
            return "LOW";
        } else {
            return "IN_STOCK";
        }
    }

    public void displayProductInfo() {
        System.out.println("Product ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Price: " + String.format("%.2f", price));
        System.out.println("Quantity: " + quantity);
        if (category != null) {
            System.out.println("Category: " + category.getName());
        } else {
            System.out.println("Category: Not assigned");
        }
        System.out.println("Stock Status: " + getStockStatus());
        System.out.println("Total Value: " + String.format("%.2f", calculateTotalValue()));
    }

    // Guarded setters
    public boolean trySetId(String id) {
        if (id != null && id.trim().length() >= 2) {
            this.id = id.trim();
            return true;
        }
        return false;
    }

    public boolean trySetName(String name) {
        if (name != null && name.trim().length() >= 2) {
            this.name = name.trim();
            return true;
        }
        return false;
    }

    public boolean trySetDescription(String description) {
        if (description == null || description.trim().length() <= 200) {
            this.description = (description == null) ? null : description.trim();
            return true;
        }
        return false;
    }

    public boolean trySetPrice(double price) {
        if (price >= 0.0 && price <= 1_000_000.0) {
            this.price = price;
            return true;
        }
        return false;
    }

    public boolean trySetQuantity(int quantity) {
        if (quantity >= 0 && quantity <= 1_000_000) {
            this.quantity = quantity;
            return true;
        }
        return false;
    }

    public boolean trySetCategory(Category category) {
        if (category != null) {
            this.category = category;
            return true;
        }
        return false;
    }

    // Overloaded pricing helpers (no tax/shipping here)
    public double finalPrice() {
        return getPrice();
    }

    public double finalPrice(int qty) {
        if (qty <= 0) return 0.0;
        return getPrice() * qty;
    }

    public double finalPrice(int qty, PricePolicy policy) {
        if (qty <= 0) return 0.0;
        if (policy == null || !policy.applicableTo(this)) return finalPrice(qty);
        return policy.apply(this, qty);
    }

    public double finalPrice(int qty, List<PricePolicy> policies) {
        if (qty <= 0) return 0.0;
        if (policies == null || policies.isEmpty()) return finalPrice(qty);
        double best = Double.POSITIVE_INFINITY;
        for (PricePolicy pp : policies) {
            double v = finalPrice(qty, pp);
            if (v < best) best = v;
        }
        return best;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", currency=" + DEFAULT_CURRENCY +
                '}';
    }
}
