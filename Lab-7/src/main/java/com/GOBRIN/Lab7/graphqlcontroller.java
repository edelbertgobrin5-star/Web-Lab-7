package com.GOBRIN.Lab7;



import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class GraphqlController {

    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public GraphqlController() {
        products.add(new Product(idCounter.getAndIncrement(), "tanduay", 999.99));
        products.add(new Product(idCounter.getAndIncrement(), "redhorse", 699.99));
        products.add(new Product(idCounter.getAndIncrement(), "", 299.99));
    }

    @QueryMapping
    public List<Product> products() {
        return new ArrayList<>(products);
    }

    @QueryMapping
    public Product product(@Argument Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @QueryMapping
    public List<Product> searchProducts(@Argument String name) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @MutationMapping
    public Product createProduct(@Argument String name, @Argument Double price) {
        Product newProduct = new Product(idCounter.getAndIncrement(), name, price);
        products.add(newProduct);
        return newProduct;
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument String name, @Argument Double price) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                if (name != null) product.setName(name);
                if (price != null) product.setPrice(price);
                return product;
            }
        }
        return null;
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return products.removeIf(product -> product.getId().equals(id));
    }

    @QueryMapping
    public String hello() {
        return "Hello from GraphQL Controller!";
    }

    @QueryMapping
    public String greet(@Argument String name) {
        return "Hello, " + name + "! Welcome to GraphQL";
    }

    public static class Product {
        private Long id;
        private String name;
        private Double price;

        public Product() {}
        public Product(Long id, String name, Double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
    }
}
this.id