package com.example.order.service;

import com.example.order.dto.OrderResponseDTO;
import com.example.order.entity.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Optional<OrderResponseDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO);
    }

    private OrderResponseDTO convertToDTO(Order order) {
        List<OrderResponseDTO.OrderItemDTO> items = order.getOrderItems().stream()
                .map(item -> new OrderResponseDTO.OrderItemDTO(
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),
                order.getName(),
                order.getTotal(),
                order.getCreatedAt(),
                items
        );
    }

    // Mock data for testing purposes
    public void createMockData() {
        // This method is just for demonstration - in a real app, you would seed the database
        // or use @PostConstruct to populate with sample data
    }
}