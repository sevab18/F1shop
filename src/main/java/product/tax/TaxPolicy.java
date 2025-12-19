package product.tax;

import product.Product;

public interface TaxPolicy {
    double taxAmount(Product product, double netPrice);

    default String name() {
        return getClass().getSimpleName();
    }
}
