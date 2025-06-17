package com.amrfawzi.ecommerce.product_service.dto;


import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    private String name;
    private String description;

    @Min(0)
    private Double price;

    @Min(0)
    private Integer quantity;

    private String category;
}

