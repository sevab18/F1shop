package com.example.javashop.api.dto;

public class CartItemRequest {
    private String productId;
    private int quantity;
    private String color;
    private boolean withPrint;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isWithPrint() {
        return withPrint;
    }

    public void setWithPrint(boolean withPrint) {
        this.withPrint = withPrint;
    }
}
