package product.tax;

import product.DigitalProduct;
import product.Product;

public class ReducedDigitalVat implements TaxPolicy {
    private final double rate;

    public ReducedDigitalVat(double rate) {
        this.rate = Math.max(0.0, rate);
    }

    @Override
    public double taxAmount(Product product, double netPrice) {
        if (product instanceof DigitalProduct) {
            return netPrice * rate;
        }
        return 0.0;
    }

    @Override
    public String name() {
        return "DigitalVAT-" + (int) (rate * 100) + "%";
    }
}
