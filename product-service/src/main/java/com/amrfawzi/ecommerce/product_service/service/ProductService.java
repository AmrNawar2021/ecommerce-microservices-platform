package com.amrfawzi.ecommerce.product_service.service;

import com.amrfawzi.ecommerce.product_service.dto.CreateProductRequest;
import com.amrfawzi.ecommerce.product_service.dto.ProductResponse;
import com.amrfawzi.ecommerce.product_service.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request, Long ownerId, List<String> roles);
    ProductResponse updateProduct(Long productId, UpdateProductRequest request, Long ownerId, List<String> roles);
    void deleteProduct(Long productId, Long ownerId, List<String> roles);
    ProductResponse getProductById(Long productId);
    List<ProductResponse> getMyProducts();
}
