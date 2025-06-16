package com.amr.ecommerce.order_service.dto;


import com.amr.ecommerce.order_service.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
}

