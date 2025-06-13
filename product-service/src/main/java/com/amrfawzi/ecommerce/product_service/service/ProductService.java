package com.amrfawzi.ecommerce.product_service.service;


import com.amrfawzi.ecommerce.product_service.dto.CreateProductRequest;
import com.amrfawzi.ecommerce.product_service.dto.ProductResponse;
import com.amrfawzi.ecommerce.product_service.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse updateProduct(Long productId, UpdateProductRequest request);

    void deleteProduct(Long productId);

    ProductResponse getProductById(Long productId);

    List<ProductResponse> getAllProducts();
}

