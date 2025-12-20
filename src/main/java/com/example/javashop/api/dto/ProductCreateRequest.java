package com.example.javashop.api.dto;

import java.util.List;
import java.util.Map;

public class ProductCreateRequest {
    private String id;
    private String name;
    private String description;
    private String categoryId;
    private String type; // "physical" or "digital"
    private double price;
    private double weightKg;
    private double downloadSizeMb;
    private boolean printAvailable;
    private Map<String, Integer> colors;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public double getDownloadSizeMb() {
        return downloadSizeMb;
    }

    public void setDownloadSizeMb(double downloadSizeMb) {
        this.downloadSizeMb = downloadSizeMb;
    }

    public boolean isPrintAvailable() {
        return printAvailable;
    }

    public void setPrintAvailable(boolean printAvailable) {
        this.printAvailable = printAvailable;
    }

    public Map<String, Integer> getColors() {
        return colors;
    }

    public void setColors(Map<String, Integer> colors) {
        this.colors = colors;
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
