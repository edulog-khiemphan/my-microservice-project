package com.example.product.service;

import com.example.product.dto.ProductResponseDTO;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Mock data for demonstration purposes
    private static final List<Product> MOCK_PRODUCTS = Arrays.asList(
        new Product(1L, "Laptop", 1200.0),
        new Product(2L, "Smartphone", 800.0),
        new Product(3L, "Tablet", 500.0)
    );

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<ProductResponseDTO> getProductById(Long id) {
        // For demo purposes, we'll use mock data instead of database
        return MOCK_PRODUCTS.stream()
                .filter(product -> product.getId().equals(id))
                .map(product -> new ProductResponseDTO(product.getId(), product.getName(), product.getPrice()))
                .findFirst();
    }
}
