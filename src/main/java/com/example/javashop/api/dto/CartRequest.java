package com.example.javashop.api.dto;

import java.util.List;

public class CartRequest {
    private List<CartItemRequest> items;
    private String delivery;

    public List<CartItemRequest> getItems() {
        return items;
    }

    public void setItems(List<CartItemRequest> items) {
        this.items = items;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
