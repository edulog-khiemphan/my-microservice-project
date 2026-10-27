package com.example.order.controller;

import com.example.order.dto.OrderResponseDTO;
import com.example.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getOrderById_ExistingId_ReturnsOrder() throws Exception {
        // Given
        Long orderId = 1L;
        OrderResponseDTO.OrderItemDTO item = new OrderResponseDTO.OrderItemDTO(
                "Laptop", 1, BigDecimal.valueOf(1200.0), BigDecimal.valueOf(1200.0));

        OrderResponseDTO mockOrder = new OrderResponseDTO(
                orderId,
                "Sample Order",
                BigDecimal.valueOf(1200.0),
                LocalDateTime.now(),
                List.of(item)
        );

        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(mockOrder));

        // When & Then
        mockMvc.perform(get("/api/orders/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.name").value("Sample Order"))
                .andExpect(jsonPath("$.total").value(1200.0))
                .andExpect(jsonPath("$.items[0].productName").value("Laptop"))
                .andExpect(jsonPath("$.items[0].quantity").value(1))
                .andExpect(jsonPath("$.items[0].price").value(1200.0));
    }

    @Test
    void getOrderById_NonExistingId_ReturnsNotFound() throws Exception {
        // Given
        Long orderId = 999L;
        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/orders/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
