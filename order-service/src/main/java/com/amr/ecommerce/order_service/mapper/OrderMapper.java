package com.amr.ecommerce.order_service.mapper;


import com.amr.ecommerce.order_service.dto.CreateOrderRequest;
import com.amr.ecommerce.order_service.dto.OrderResponse;
import com.amr.ecommerce.order_service.model.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderEntity toEntity(CreateOrderRequest request, Long userId, Double totalPrice) {
        return OrderEntity.builder()
                .userId(userId)
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .totalPrice(totalPrice)
                .build();
    }

    public OrderResponse toResponse(OrderEntity entity) {
        return OrderResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .totalPrice(entity.getTotalPrice())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
