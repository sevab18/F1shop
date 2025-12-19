package product.pricing;

import product.Product;

public class BogoHalf implements PricePolicy {
    @Override
    public String name() {
        return "BOGO-HALF";
    }

    @Override
    public double apply(Product p, int qty) {
        double price = p.getPrice();
        int pairs = Math.max(0, qty) / 2;
        int singles = Math.max(0, qty) % 2;
        return pairs * (price * 1.5) + singles * price;
    }
}
