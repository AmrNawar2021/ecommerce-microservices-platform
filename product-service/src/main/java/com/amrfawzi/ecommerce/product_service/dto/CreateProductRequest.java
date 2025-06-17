package com.amrfawzi.ecommerce.product_service.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Min(0)
    private Double price;

    @NotNull
    @Min(0)
    private Integer quantity;

    private String category;
}

