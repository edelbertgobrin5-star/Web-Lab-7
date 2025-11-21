package com.GOBRIN.Lab7;

import com.GOBRIN.Lab7.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(4); // Start from 4 since we add 3 mock products

    // Initialize with mock products as required
    public ProductService() {
        // Add 3 mock products as specified in requirements
        products.add(new Product(1L, "Laptop Pro", 999.99));
        products.add(new Product(2L, "Wireless Mouse", 29.99));
        products.add(new Product(3L, "Mechanical Keyboard", 79.99));
    }

    // Get all products
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    // Get product by ID
    public Product getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Create new product - assign new unique ID
    public Product createProduct(Product product) {
        Long newId = idCounter.getAndIncrement();
        product.setId(newId);
        products.add(product);
        return product;
    }

    // Update existing product
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            existingProduct.setName(productDetails.getName());
            existingProduct.setPrice(productDetails.getPrice());
            return existingProduct;
        }
        return null;
    }

    // Delete product
    public boolean deleteProduct(Long id) {
        return products.removeIf(product -> product.getId().equals(id));
    }
}