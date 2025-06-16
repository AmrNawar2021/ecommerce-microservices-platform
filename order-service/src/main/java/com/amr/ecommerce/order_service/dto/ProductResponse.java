package com.amr.ecommerce.order_service.dto;


import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private Integer availableQuantity;
}
