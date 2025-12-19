package com.example.javashop.catalog;

import com.example.javashop.api.dto.CartItemPrice;
import com.example.javashop.api.dto.CartItemRequest;
import com.example.javashop.api.dto.CartPriceResponse;
import product.DigitalProduct;
import product.PhysicalProduct;
import product.Product;
import product.pricing.BogoHalf;
import product.pricing.FixedOff;
import product.pricing.PercentageOff;
import product.pricing.PricePolicy;
import product.tax.FlatVat;
import product.tax.NoTax;
import product.tax.ReducedDigitalVat;
import product.tax.TaxPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PricingEngine {

    private final List<PricePolicy> defaultPolicies = List.of(
            new PercentageOff(10),
            new FixedOff(1_000),
            new BogoHalf()
    );

    private final TaxPolicy physicalTax = new FlatVat(0.12);
    private final TaxPolicy digitalTax = new ReducedDigitalVat(0.05);
    private final TaxPolicy noTax = new NoTax();

    public CartPriceResponse priceCart(List<CartItemRequest> items, Map<String, CatalogProduct> productIndex, String delivery) {
        CartPriceResponse response = new CartPriceResponse();
        if (items == null || items.isEmpty()) {
            response.setShipping(0);
            response.setGrandTotal(0);
            return response;
        }

        boolean hasPhysical = false;
        double subtotal = 0;
        double taxTotal = 0;
        List<CartItemPrice> pricedItems = new ArrayList<>();
        List<Double> unitNets = new ArrayList<>();

        for (CartItemRequest req : items) {
            if (req == null || req.getProductId() == null) continue;
            CatalogProduct cp = productIndex.get(req.getProductId());
            if (cp == null) continue;

            Product p = cp.getProduct();
            int qty = Math.max(1, req.getQuantity());

            double colorSurcharge = 0;
            if (req.getColor() != null && cp.getColors() != null) {
                colorSurcharge = cp.getColors().getOrDefault(req.getColor(), 0);
            }
            double unitPrice = p.getPrice() + colorSurcharge;

            boolean isSale = "sale".equalsIgnoreCase(cp.getCategoryId());

            PricePolicy appliedPolicy = null;
            double bestNet = unitPrice * qty;
            double undiscounted = unitPrice * qty;
            double discountApplied = 0;

            if (!isSale) {
                appliedPolicy = choosePolicy(p, qty, unitPrice);
                bestNet = applyPolicy(p, qty, unitPrice, appliedPolicy);
                discountApplied = Math.max(0, undiscounted - bestNet);
            }

            TaxPolicy taxPolicy = isSale ? noTax : resolveTax(p);
            double tax = taxPolicy.taxAmount(p, bestNet);

            CartItemPrice itemPrice = new CartItemPrice();
            itemPrice.setProductId(p.getId());
            itemPrice.setName(p.getName());
            itemPrice.setQuantity(qty);
            itemPrice.setUnitPrice(unitPrice);
            itemPrice.setDiscountApplied(discountApplied);
            itemPrice.setAppliedPolicy(appliedPolicy != null ? appliedPolicy.name() : (isSale ? "SALE" : "NONE"));
            itemPrice.setNet(bestNet);
            itemPrice.setTax(tax);
            itemPrice.setTotal(bestNet + tax);
            itemPrice.setImage(cp.getImages() != null && !cp.getImages().isEmpty() ? cp.getImages().get(0) : cp.getImage());

            pricedItems.add(itemPrice);

            subtotal += bestNet;
            taxTotal += tax;
            if (p instanceof PhysicalProduct) {
                hasPhysical = true;
            }

            if (!isSale) {
                double unitNet = bestNet / qty;
                for (int i = 0; i < qty; i++) {
                    unitNets.add(unitNet);
                }
            }
        }

        double originalSubtotal = subtotal;
        double promoDiscount = calculateThirdItemDiscount(unitNets);
        double adjustedSubtotal = Math.max(0.0, originalSubtotal - promoDiscount);
        double adjustedTax = originalSubtotal > 0 ? taxTotal * (adjustedSubtotal / originalSubtotal) : 0.0;
        double shipping = hasPhysical ? 500.0 : 0.0;
        if (delivery != null && delivery.equalsIgnoreCase("Самовывоз")) {
            shipping = 0.0;
        }
        response.setItems(pricedItems);
        response.setSubtotal(adjustedSubtotal);
        response.setTaxTotal(adjustedTax);
        response.setShipping(shipping);
        response.setPromoDiscount(promoDiscount);
        response.setGrandTotal(Math.max(0.0, adjustedSubtotal + adjustedTax + shipping));
        response.setGift("Новогодний шар (подарок)");
        response.setGiftImage("/images/Новогодний%20шар.webp");
        return response;
    }

    private PricePolicy choosePolicy(Product p, int qty, double unitPrice) {
        if (qty <= 0) return null;
        double best = Double.POSITIVE_INFINITY;
        PricePolicy applied = null;
        for (PricePolicy pp : defaultPolicies) {
            if (!pp.applicableTo(p)) continue;
            double v = applyPolicy(p, qty, unitPrice, pp);
            if (v < best) {
                best = v;
                applied = pp;
            }
        }
        return applied;
    }

    private double applyPolicy(Product p, int qty, double unitPrice, PricePolicy policy) {
        double originalPrice = p.getPrice();
        try {
            p.trySetPrice(unitPrice);
            if (policy == null) {
                return p.finalPrice(qty);
            }
            return p.finalPrice(qty, policy);
        } finally {
            p.trySetPrice(originalPrice);
        }
    }

    private TaxPolicy resolveTax(Product p) {
        if (p instanceof PhysicalProduct) {
            return physicalTax;
        } else if (p instanceof DigitalProduct) {
            return digitalTax;
        }
        return noTax;
    }

    private double calculateThirdItemDiscount(List<Double> unitNets) {
        if (unitNets == null || unitNets.isEmpty()) return 0.0;
        unitNets.sort(Double::compareTo);
        double discount = 0.0;
        for (int i = 2; i < unitNets.size(); i += 3) {
            discount += unitNets.get(i) * 0.5;
        }
        return discount;
    }
}
