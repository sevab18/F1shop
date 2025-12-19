package com.example.javashop.api;

import category.Category;
import com.example.javashop.api.dto.CartPriceResponse;
import com.example.javashop.api.dto.CartRequest;
import com.example.javashop.api.dto.CategoryResponse;
import com.example.javashop.api.dto.ProductResponse;
import com.example.javashop.catalog.CatalogProduct;
import com.example.javashop.catalog.CatalogService;
import com.example.javashop.catalog.PricingEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CatalogController {

    private final CatalogService catalogService;
    private final PricingEngine pricingEngine;

    public CatalogController() {
        this.catalogService = new CatalogService();
        this.pricingEngine = new PricingEngine();
    }

    @GetMapping("/categories")
    public List<CategoryResponse> categories() {
        return catalogService.getCategories()
                .stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/products")
    public List<ProductResponse> products(@RequestParam(value = "category", required = false) String category) {
        return catalogService.getProducts(category);
    }

    @PostMapping("/cart/price")
    public ResponseEntity<CartPriceResponse> priceCart(@RequestBody CartRequest request) {
        CartPriceResponse resp = pricingEngine.priceCart(
                request != null ? request.getItems() : null,
                catalogService.getProductIndex(),
                request != null ? request.getDelivery() : null
        );
        return ResponseEntity.ok(resp);
    }

    private CategoryResponse toCategoryResponse(Category c) {
        return new CategoryResponse(c.getId(), c.getName(), c.getDescription());
    }
}
