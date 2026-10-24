package com.example.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        String name,
        BigDecimal total,
        LocalDateTime createdAt,
        List<OrderItemDTO> items
) {
    public record OrderItemDTO(
            String productName,
            Integer quantity,
            BigDecimal price,
            BigDecimal totalPrice
    ) {}
}