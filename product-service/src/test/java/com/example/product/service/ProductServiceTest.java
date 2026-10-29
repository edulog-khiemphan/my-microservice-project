package com.example.product.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.product.dto.ProductResponseDTO;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.impl.ProductServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    // --- Helper method to create a dummy Product ---
    private Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(Double.valueOf(100.0));
        product.setStockQuantity(10);
        return product;
    }

    // ---------------------------------------------------------
    // Test for: getProductById
    // ---------------------------------------------------------

    @Test
    void getProductById_ShouldReturnProductDTO_WhenProductExists() {
        // Arrange
        Long id = 1L;
        Product product = createProduct();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // Act
        Optional<ProductResponseDTO> result = productService.getProductById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product.getId(), result.get().id());
        assertEquals(product.getName(), result.get().name());
        assertEquals(product.getDescription(), result.get().description());
        assertEquals(product.getPrice(), result.get().price());
        assertEquals(product.getStockQuantity(), result.get().stockQuantity());

        
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void getProductById_ShouldReturnEmpty_WhenProductDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<ProductResponseDTO> result = productService.getProductById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(id);
    }

    // ---------------------------------------------------------
    // Test for: getAllProducts
    // ---------------------------------------------------------

    @Test
    void getAllProducts_ShouldReturnPageOfDTOs() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Product product = createProduct();
        Page<Product> productPage = new PageImpl<>(List.of(product));
        
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // Act
        Page<ProductResponseDTO> result = productService.getAllProducts(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(product.getName(), result.getContent().get(0).name());
        
        verify(productRepository, times(1)).findAll(pageable);
    }

    // ---------------------------------------------------------
    // Test for: searchProducts
    // ---------------------------------------------------------

    @Test
    void searchProducts_ShouldReturnPageOfDTOs_WhenQueryMatches() {
        // Arrange
        String query = "Test";
        Pageable pageable = PageRequest.of(0, 10);
        Product product = createProduct();
        Page<Product> productPage = new PageImpl<>(List.of(product));

        when(productRepository.findBySearchQuery(query, pageable)).thenReturn(productPage);

        // Act
        Page<ProductResponseDTO> result = productService.searchProducts(query, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(product.getName(), result.getContent().get(0).name());

        verify(productRepository, times(1)).findBySearchQuery(query, pageable);
    }
    
    @Test
    void searchProducts_ShouldReturnEmptyPage_WhenNoMatch() {
        // Arrange
        String query = "NonExistent";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> emptyPage = Page.empty();

        when(productRepository.findBySearchQuery(query, pageable)).thenReturn(emptyPage);

        // Act
        Page<ProductResponseDTO> result = productService.searchProducts(query, pageable);

        // Assert
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findBySearchQuery(query, pageable);
    }
}