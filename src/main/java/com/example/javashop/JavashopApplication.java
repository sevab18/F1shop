package com.example.javashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.javashop", "product", "category"})
public class JavashopApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavashopApplication.class, args);
    }
}
