package com.example.javashop.api.dto;

import java.util.Map;

import java.util.List;

public class ProductResponse {
    private String id;
    private String name;
    private String categoryId;
    private String description;
    private double basePrice;
    private String specs;
    private Map<String, Integer> colors;
    private boolean printAvailable;
    private String image;
    private List<String> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Map<String, Integer> getColors() {
        return colors;
    }

    public void setColors(Map<String, Integer> colors) {
        this.colors = colors;
    }

    public boolean isPrintAvailable() {
        return printAvailable;
    }

    public void setPrintAvailable(boolean printAvailable) {
        this.printAvailable = printAvailable;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
