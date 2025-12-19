package product;

import product.shipping.Shippable;

public class PhysicalProduct extends Product implements Shippable {
    private double weightKg;
    private double lengthCm;
    private double widthCm;
    private double heightCm;

    // Constructors
    public PhysicalProduct() {
        super();
        this.weightKg = 0.0;
        this.lengthCm = 0.0;
        this.widthCm = 0.0;
        this.heightCm = 0.0;
    }

    public PhysicalProduct(String id, String name, double price, double weightKg) {
        super(id, name, price);
        this.weightKg = 0.0; // Default
        this.lengthCm = 0.0;
        this.widthCm = 0.0;
        this.heightCm = 0.0;
        trySetWeightKg(weightKg);
    }

    public PhysicalProduct(String id, String name, String description, double price, int quantity,
                           double weightKg, double lengthCm, double widthCm, double heightCm) {
        super(id, name, description, price, quantity, null); // Category set to null, can be set later
        this.weightKg = 0.0; // Default
        this.lengthCm = 0.0;
        this.widthCm = 0.0;
        this.heightCm = 0.0;
        trySetWeightKg(weightKg);
        trySetDimensions(lengthCm, widthCm, heightCm);
    }

    // Guarded Setters
    public boolean trySetWeightKg(double v) {
        if (v >= 0.0 && v <= 1000.0) {
            this.weightKg = v;
            return true;
        }
        return false;
    }

    public boolean trySetDimensions(double l, double w, double h) {
        if (l >= 0.0 && l <= 1000.0 &&
            w >= 0.0 && w <= 1000.0 &&
            h >= 0.0 && h <= 1000.0) {
            this.lengthCm = l;
            this.widthCm = w;
            this.heightCm = h;
            return true;
        }
        return false;
    }

    // Business Logic
    public double estimateShippingCost() {
        double volumetric = (lengthCm * widthCm * heightCm) / 5000.0;
        double billable = Math.max(weightKg, volumetric);
        return billable * 100; // KZT
    }

    // Getters for Physical Product specific fields
    public double getWeightKg() {
        return weightKg;
    }

    public double getLengthCm() {
        return lengthCm;
    }

    public double getWidthCm() {
        return widthCm;
    }

    public double getHeightCm() {
        return heightCm;
    }

    @Override
    public double finalPrice(int qty, product.pricing.PricePolicy policy) {
        double base = super.finalPrice(qty, policy);
        return base + estimateShippingCost();
    }

    @Override
    public double finalPrice(int qty, java.util.List<product.pricing.PricePolicy> policies) {
        double base = super.finalPrice(qty, policies);
        return base + estimateShippingCost();
    }

    @Override
    public void displayProductInfo() {
        super.displayProductInfo();
        System.out.println("  -- Physical Product Details --");
        System.out.println("  Weight: " + String.format("%.2f", weightKg) + " kg");
        System.out.println("  Dimensions: " + String.format("%.2f", lengthCm) + "x" + String.format("%.2f", widthCm) + "x" + String.format("%.2f", heightCm) + " cm");
        System.out.println("  Estimated Shipping Cost: " + String.format("%.2f", estimateShippingCost()) + " KZT");
    }

    @Override
    public String toString() {
        return super.toString() +
               " | Type: Physical" +
               " | Weight: " + String.format("%.2f", weightKg) + "kg" +
               " | Dimensions: " + String.format("%.2f", lengthCm) + "x" + String.format("%.2f", widthCm) + "x" + String.format("%.2f", heightCm) + "cm";
    }
}
