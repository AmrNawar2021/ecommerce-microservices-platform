package com.amr.ecommerce.order_service.provider;


import com.amr.ecommerce.order_service.dto.ProductResponse;

public interface ProductProvider {
    ProductResponse getProductById(Long productId, String jwtToken);
}
