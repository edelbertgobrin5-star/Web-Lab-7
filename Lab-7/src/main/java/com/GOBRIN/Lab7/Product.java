package com.GOBRIN.Lab7;

import java.util.*;
import java.io.*;
import java.time.*;
import java.util.concurrent.*;

public record Product(Long id, String name, Double price)
        implements Serializable, Comparable<Product> {

    // Compact constructor for validation
    public Product {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    // Factory methods
    public static Product of(Long id, String name, Double price) {
        return new Product(id, name, price);
    }

    public static Product withName(String name) {
        return new Product(1L, name, 0.0);
    }

    public static Product withPrice(Double price) {
        return new Product(1L, "Unknown", price);
    }

    // Business logic methods
    public Product withDiscount(Double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        double discountedPrice = price * (1 - discountPercentage / 100);
        return new Product(id, name, discountedPrice);
    }

    public Product withNameUpdate(String newName) {
        return new Product(id, newName, price);
    }

    public boolean isExpensive() {
        return price > 100.0;
    }

    public boolean isAffordable() {
        return price <= 50.0;
    }

    // Formatted display methods
    public String toDisplayString() {
        return String.format("""
            Product Details:
            - ID: %d
            - Name: %s
            - Price: $%.2f
            """, id, name, price);
    }

    public String toJSON() {
        return String.format("""
            {
                "id": %d,
                "name": "%s",
                "price": %.2f
            }
            """, id, name, price);
    }

    // For comparison
    @Override
    public int compareTo(Product other) {
        return this.price.compareTo(other.price);
    }

    // ADD THIS MAIN METHOD TO TEST YOUR RECORD
    public static void main(String[] args) {
        try {
            System.out.println("=== TESTING PRODUCT RECORD ===\n");

            // Test 1: Create products using different methods
            System.out.println("1. Creating Products:");
            Product laptop = new Product(1L, "Laptop", 999.99);
            Product mouse = Product.of(2L, "Mouse", 25.50);
            Product unknownProduct = Product.withName("Keyboard");
            Product pricedProduct = Product.withPrice(49.99);

            System.out.println(laptop.toDisplayString());
            System.out.println(mouse.toDisplayString());
            System.out.println(unknownProduct.toDisplayString());
            System.out.println(pricedProduct.toDisplayString());

            // Test 2: Business logic
            System.out.println("\n2. Business Logic Tests:");
            System.out.println("Is laptop expensive? " + laptop.isExpensive());
            System.out.println("Is mouse affordable? " + mouse.isAffordable());
            System.out.println("Is priced product affordable? " + pricedProduct.isAffordable());

            // Test 3: Discount application
            System.out.println("\n3. Discount Tests:");
            Product discountedLaptop = laptop.withDiscount(15.0);
            System.out.println("Original price: $" + laptop.price());
            System.out.println("After 15% discount: $" + discountedLaptop.price());

            // Test 4: Name update
            System.out.println("\n4. Update Tests:");
            Product updatedMouse = mouse.withNameUpdate("Wireless Mouse");
            System.out.println("Original: " + mouse.name());
            System.out.println("Updated: " + updatedMouse.name());

            // Test 5: JSON output
            System.out.println("\n5. JSON Output:");
            System.out.println(laptop.toJSON());

            // Test 6: Comparison
            System.out.println("\n6. Comparison Tests:");
            System.out.println("Laptop vs Mouse: " + laptop.compareTo(mouse));
            System.out.println("Mouse vs Priced Product: " + mouse.compareTo(pricedProduct));

            // Test 7: Validation (should throw exceptions)
            System.out.println("\n7. Validation Tests:");
            try {
                Product invalid = new Product(-1L, "Test", 10.0);
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Correctly caught invalid ID: " + e.getMessage());
            }

            try {
                Product invalid = new Product(1L, "", 10.0);
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Correctly caught blank name: " + e.getMessage());
            }

            try {
                Product invalid = new Product(1L, "Test", -10.0);
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Correctly caught negative price: " + e.getMessage());
            }

            System.out.println("\n=== ALL TESTS COMPLETED SUCCESSFULLY ===");

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}