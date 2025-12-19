package product.pricing;

import product.Product;

public interface PricePolicy {
    String name();

    /**
     * Calculate final cost for qty units of product (no tax/shipping).
     */
    double apply(Product p, int qty);

    default boolean applicableTo(Product p) {
        return true;
    }
}
