package com.example.product.controller;

import com.example.product.dto.ProductResponseDTO;
import com.example.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getProductById_ExistingId_ReturnsProduct() throws Exception {
        // Given
        Long productId = 1L;
        ProductResponseDTO mockProduct = new ProductResponseDTO(productId, "Laptop", "", 1200.0, 2000);
        when(productService.getProductById(productId)).thenReturn(Optional.of(mockProduct));

        // When & Then
        mockMvc.perform(get("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1200.0));
    }

    @Test
    void getProductById_NonExistingId_ReturnsNotFound() throws Exception {
        // Given
        Long productId = 999L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}