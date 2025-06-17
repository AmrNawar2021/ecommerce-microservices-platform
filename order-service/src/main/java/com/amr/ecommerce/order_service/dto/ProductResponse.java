package com.amr.ecommerce.order_service.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
