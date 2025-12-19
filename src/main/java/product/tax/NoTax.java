package product.tax;

import product.Product;

public class NoTax implements TaxPolicy {
    @Override
    public double taxAmount(Product product, double netPrice) {
        return 0.0;
    }
}
