package product;

public class DigitalProduct extends Product {
    private double downloadSizeMb;
    private String licenseKey;

    // Constructors
    public DigitalProduct() {
        super();
        this.downloadSizeMb = 0.0;
        this.licenseKey = null;
    }

    public DigitalProduct(String id, String name, double price, double downloadSizeMb) {
        super(id, name, price);
        this.downloadSizeMb = 0.0; // Default
        this.licenseKey = null;
        trySetDownloadSizeMb(downloadSizeMb);
    }

    public DigitalProduct(String id, String name, String description, double price, int quantity,
                          double downloadSizeMb, String licenseKey) {
        super(id, name, description, price, quantity, null); // Category set to null, can be set later
        this.downloadSizeMb = 0.0; // Default
        this.licenseKey = null;
        trySetDownloadSizeMb(downloadSizeMb);
        trySetLicenseKey(licenseKey);
    }

    // Guarded Setters
    public boolean trySetDownloadSizeMb(double v) {
        if (v >= 0.0 && v <= 1_000_000.0) {
            this.downloadSizeMb = v;
            return true;
        }
        return false;
    }

    public boolean trySetLicenseKey(String key) {
        if (key == null || key.length() <= 64) {
            this.licenseKey = key;
            return true;
        }
        return false;
    }

    // Business Logic
    public boolean isLicenseRequired() {
        return licenseKey != null && !licenseKey.isBlank();
    }

    // Getters for Digital Product specific fields
    public double getDownloadSizeMb() {
        return downloadSizeMb;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    @Override
    public double finalPrice(int qty, product.pricing.PricePolicy policy) {
        // Digital items ignore BOGO half-off; everything else works
        if (policy instanceof product.pricing.BogoHalf) {
            return super.finalPrice(qty);
        }
        return super.finalPrice(qty, policy);
    }

    @Override
    public void displayProductInfo() {
        super.displayProductInfo();
        System.out.println("  -- Digital Product Details --");
        System.out.println("  Download Size: " + String.format("%.2f", downloadSizeMb) + " MB");
        System.out.println("  License Required: " + isLicenseRequired());
        if (isLicenseRequired()) {
            System.out.println("  License Key: " + licenseKey);
        }
    }

    @Override
    public String toString() {
        return super.toString() +
               " | Type: Digital" +
               " | Download Size: " + String.format("%.2f", downloadSizeMb) + "MB" +
               " | License Required: " + isLicenseRequired();
    }
}
