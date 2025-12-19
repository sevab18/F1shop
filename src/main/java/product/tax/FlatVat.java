package product.tax;

import product.Product;

public class FlatVat implements TaxPolicy {
    private final double rate;

    public FlatVat(double rate) {
        this.rate = Math.max(0.0, rate);
    }

    @Override
    public double taxAmount(Product product, double netPrice) {
        return netPrice * rate;
    }

    @Override
    public String name() {
        return "VAT-" + (int) (rate * 100) + "%";
    }
}
