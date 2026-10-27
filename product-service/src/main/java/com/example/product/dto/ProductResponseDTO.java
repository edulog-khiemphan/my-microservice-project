package com.example.product.dto;

public record ProductResponseDTO(Long id, String name, String description, Double price, Integer stockQuantity) {
}