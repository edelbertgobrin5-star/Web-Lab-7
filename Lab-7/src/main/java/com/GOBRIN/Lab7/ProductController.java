package com.GOBRIN.Lab7;

import com.GOBRIN.Lab7.Product;
import com.GOBRIN.Lab7.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Constructor injection as required
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // READ ALL Products - GET /api/products - 200 OK
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // 200 OK
    }

    // READ ONE Product - GET /api/products/{id} - 200 OK or 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // CREATE a new Product - POST /api/products - 201 Created
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct); // 201 Created
    }

    // UPDATE an existing Product - PUT /api/products/{id} - 200 OK or 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // DELETE a Product - DELETE /api/products/{id} - 200 OK
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found (optional)
        }
    }
}