package com.example.javashop.api.dto;

import java.util.ArrayList;
import java.util.List;

public class CartPriceResponse {
    private List<CartItemPrice> items = new ArrayList<>();
    private double subtotal;
    private double taxTotal;
    private double shipping;
    private double grandTotal;
    private String gift = "Новогодний шар (подарок)";
    private double promoDiscount;
    private String giftImage;

    public List<CartItemPrice> getItems() {
        return items;
    }

    public void setItems(List<CartItemPrice> items) {
        this.items = items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public double getPromoDiscount() {
        return promoDiscount;
    }

    public void setPromoDiscount(double promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }
}
