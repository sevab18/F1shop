package com.example.javashop.catalog;

import product.Product;

import java.util.List;
import java.util.Map;

public class CatalogProduct {
    private final String categoryId;
    private final Product product;
    private final String specs;
    private final Map<String, Integer> colors;
    private final boolean printAvailable;
    private final String image;
    private final List<String> images;

    public CatalogProduct(String categoryId,
                          Product product,
                          String specs,
                          Map<String, Integer> colors,
                          boolean printAvailable,
                          String image,
                          List<String> images) {
        this.categoryId = categoryId;
        this.product = product;
        this.specs = specs;
        this.colors = colors;
        this.printAvailable = printAvailable;
        this.image = image;
        this.images = images;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Product getProduct() {
        return product;
    }

    public String getSpecs() {
        return specs;
    }

    public Map<String, Integer> getColors() {
        return colors;
    }

    public boolean isPrintAvailable() {
        return printAvailable;
    }

    public String getImage() {
        return image;
    }

    public List<String> getImages() {
        return images;
    }
}
