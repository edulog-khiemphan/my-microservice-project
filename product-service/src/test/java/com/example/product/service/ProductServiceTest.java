package com.example.product.service;

import com.example.product.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Test
    void getProductById_ExistingId_ReturnsProduct() {
        // Given
        ProductService productService = new ProductService(null);

        // When
        Optional<ProductResponseDTO> result = productService.getProductById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
        assertEquals("Laptop", result.get().name());
        assertEquals(1200.0, result.get().price());
    }

    @Test
    void getProductById_NonExistingId_ReturnsEmpty() {
        // Given
        ProductService productService = new ProductService(null);

        // When
        Optional<ProductResponseDTO> result = productService.getProductById(999L);

        // Then
        assertFalse(result.isPresent());
    }
}