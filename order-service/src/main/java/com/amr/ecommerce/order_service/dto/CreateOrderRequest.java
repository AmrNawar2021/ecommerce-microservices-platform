package com.amr.ecommerce.order_service.dto;


import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long productId;
    private Integer quantity;
}


