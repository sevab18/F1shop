package com.example.javashop.catalog;

import category.Category;
import com.example.javashop.api.dto.ProductResponse;
import product.PhysicalProduct;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CatalogService {
    private final Map<String, Category> categories = new LinkedHashMap<>();
    private final Map<String, CatalogProduct> productIndex = new LinkedHashMap<>();

    public CatalogService() {
        seedData();
    }

    public synchronized ProductResponse addDynamicProduct(com.example.javashop.api.dto.ProductCreateRequest req) {
        if (req == null) throw new IllegalArgumentException("Request is required");
        if (req.getName() == null || req.getName().isBlank()) throw new IllegalArgumentException("Name is required");
        if (req.getCategoryId() == null || req.getCategoryId().isBlank()) throw new IllegalArgumentException("Category is required");
        if (req.getPrice() <= 0) throw new IllegalArgumentException("Price must be > 0");

        Category category = categories.get(req.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("Category not found: " + req.getCategoryId());
        }

        String id = (req.getId() == null || req.getId().isBlank()) ? "DYN-" + (productIndex.size() + 1) : req.getId();
        String type = req.getType() == null ? "physical" : req.getType().toLowerCase();

        Product product;
        if ("digital".equals(type)) {
            product = new product.DigitalProduct(id, req.getName(), Math.max(0, req.getPrice()), Math.max(0, req.getDownloadSizeMb()));
        } else {
            product = new PhysicalProduct(id, req.getName(), Math.max(0, req.getPrice()), Math.max(0, req.getWeightKg()));
        }
        if (req.getDescription() != null) {
            product.trySetDescription(req.getDescription());
        }

        Map<String, Integer> colors = req.getColors() == null ? Map.of() : new HashMap<>(req.getColors());
        String image = req.getImage() != null ? req.getImage() : "";
        List<String> images = req.getImages() != null && !req.getImages().isEmpty() ? new ArrayList<>(req.getImages()) : List.of(image);

        addProduct(category, product, req.getDescription(), colors, req.isPrintAvailable(), image, images);
        return toResponse(productIndex.get(product.getId()));
    }

    public synchronized boolean deleteProduct(String productId) {
        CatalogProduct removed = productIndex.remove(productId);
        if (removed == null) return false;
        Category c = categories.get(removed.getCategoryId());
        if (c != null) {
            c.removeProduct(removed.getProduct());
        }
        return true;
    }

    private void seedData() {
        Category tech = new Category("tech", "Техника", "Ноутбуки и мобильные устройства");
        Category digital = new Category("digital", "Цифровые товары", "Электронные книги");
        Category accessories = new Category("accessories", "Аксессуары", "Удобные мелочи для техники");
        Category sale = new Category("sale", "Распродажа", "Спецпредложения и мерч");

        categories.put(tech.getId(), tech);
        categories.put(digital.getId(), digital);
        categories.put(accessories.getId(), accessories);
        categories.put(sale.getId(), sale);

        // Техника
        addProduct(tech, new PhysicalProduct("101", "MacBook Pro 14\"", 1_000_000, 1.8),
                "M3, 16GB RAM, 512GB SSD",
                Map.of(),
                false,
                "/images/mac14-1.jpg",
                List.of("/images/mac14-1.jpg", "/images/mac14-2.jpg", "/images/mac14-3.jpg"));

        addProduct(tech, new PhysicalProduct("102", "MacBook Pro 16\"", 1_000_000, 2.1),
                "M3 Pro, 18GB RAM, 1TB SSD",
                Map.of(),
                false,
                "/images/mac16-1.jpg",
                List.of("/images/mac16-1.jpg", "/images/mac16-2.jpg", "/images/mac16-3.jpg"));

        addProduct(tech, new PhysicalProduct("107", "iPhone 16", 700_000, 0.25),
                "A18 Bionic, 256GB",
                Map.of(),
                false,
                "/images/iphone16-1.png",
                List.of("/images/iphone16-1.png", "/images/iphone16-2.png", "/images/iphone16-3.png"));

        addProduct(tech, new PhysicalProduct("108", "Apple iPad 16", 600_000, 0.6),
                "M4, 256GB Wi-Fi",
                Map.of(),
                false,
                "/images/ipad16-1.png",
                List.of("/images/ipad16-1.png", "/images/ipad16-2.png", "/images/ipad16-3.png"));

        // Цифровые товары
        addProduct(digital, new product.DigitalProduct("201", "The F1: Racing Insights", 4_500, 50),
                "Электронная книга (PDF/ePub), мгновенная загрузка",
                Map.of(),
                false,
                "/images/The%20F1book.webp",
                List.of("/images/The%20F1book.webp"));

        addProduct(digital, new product.DigitalProduct("202", "За пределом: Истории пилотов", 6_000, 55),
                "Электронная книга (PDF/ePub), эксклюзивное издание",
                Map.of(),
                false,
                "/images/za-predelom.webp",
                List.of("/images/za-predelom.webp"));

        // Аксессуары
        addProduct(accessories, new PhysicalProduct("301", "Механическая клавиатура", 45_000, 0.8),
                "Полноразмерная, hot-swap switches",
                Map.of(),
                false,
                "/images/mechanical-keyboard.png",
                List.of("/images/mechanical-keyboard.png"));

        addProduct(accessories, new PhysicalProduct("303", "USB-C кабель быстрой зарядки", 12_000, 0.2),
                "Поддержка PD, 100W",
                Map.of(),
                false,
                "/images/usb-c.png",
                List.of("/images/usb-c.png"));

        addProduct(accessories, new PhysicalProduct("304", "Беспроводная мышь", 25_000, 0.25),
                "2.4G + Bluetooth, тихие кнопки",
                Map.of(),
                false,
                "/images/wireless-mouse.webp",
                List.of("/images/wireless-mouse.webp"));

        addProduct(accessories, new PhysicalProduct("305", "Чехол для iPhone", 8_000, 0.1),
                "Тонкий, ударопрочный",
                Map.of(),
                false,
                "/images/case-iphone.webp",
                List.of("/images/case-iphone.webp"));

        addProduct(accessories, new PhysicalProduct("306", "Чехол для MacBook", 15_000, 0.4),
                "Матовый, защита от царапин",
                Map.of(),
                false,
                "/images/case-macbook.webp",
                List.of("/images/case-macbook.webp"));

        addProduct(accessories, new PhysicalProduct("307", "Защитное стекло 15\"", 6_000, 0.05),
                "Олеофобное покрытие",
                Map.of(),
                false,
                "/images/screen-iphone.webp",
                List.of("/images/screen-iphone.webp"));

        // Распродажа (без скидок и налогов)
        addProduct(sale, new PhysicalProduct("S01", "Худи новогодняя", 7_000, 0.6),
                "Тёплая худи, лимитированная серия",
                Map.of(),
                false,
                "/images/hoodie.png",
                List.of("/images/hoodie.png"));

        addProduct(sale, new PhysicalProduct("S02", "Футболка новогодняя", 2_000, 0.3),
                "Лёгкая футболка с принтом F1",
                Map.of(),
                false,
                "/images/Tshirt.png",
                List.of("/images/Tshirt.png"));

        addProduct(sale, new PhysicalProduct("S03", "Кружка праздничная", 1_000, 0.4),
                "Подарочная кружка, ограниченный тираж",
                Map.of(),
                false,
                "/images/stakan.png",
                List.of("/images/stakan.png"));
    }

    private void addProduct(Category category,
                            Product product,
                            String specs,
                            Map<String, Integer> colors,
                            boolean printAvailable,
                            String image,
                            List<String> images) {
        category.addProduct(product);
        Map<String, Integer> colorCopy = new HashMap<>(colors);
        CatalogProduct catalogProduct = new CatalogProduct(category.getId(), product, specs, colorCopy, printAvailable, image, images);
        productIndex.put(product.getId(), catalogProduct);
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categories.values());
    }

    public List<ProductResponse> getProducts(String categoryId) {
        return productIndex.values().stream()
                .filter(p -> categoryId == null || categoryId.equalsIgnoreCase("all") || p.getCategoryId().equalsIgnoreCase(categoryId))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<CatalogProduct> findById(String id) {
        return Optional.ofNullable(productIndex.get(id));
    }

    public Map<String, CatalogProduct> getProductIndex() {
        return productIndex;
    }

    private ProductResponse toResponse(CatalogProduct cp) {
        ProductResponse dto = new ProductResponse();
        dto.setId(cp.getProduct().getId());
        dto.setName(cp.getProduct().getName());
        dto.setDescription(cp.getProduct().getDescription());
        dto.setCategoryId(cp.getCategoryId());
        dto.setBasePrice(cp.getProduct().getPrice());
        dto.setSpecs(cp.getSpecs());
        dto.setColors(cp.getColors());
        dto.setPrintAvailable(cp.isPrintAvailable());
        dto.setImage(cp.getImage());
        dto.setImages(cp.getImages());
        return dto;
    }
}
