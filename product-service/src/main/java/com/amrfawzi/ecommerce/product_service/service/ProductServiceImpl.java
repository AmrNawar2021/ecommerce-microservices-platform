package com.amrfawzi.ecommerce.product_service.service;

import com.amrfawzi.ecommerce.product_service.dto.CreateProductRequest;
import com.amrfawzi.ecommerce.product_service.dto.ProductResponse;
import com.amrfawzi.ecommerce.product_service.dto.UpdateProductRequest;
import com.amrfawzi.ecommerce.product_service.model.Product;
import com.amrfawzi.ecommerce.product_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private boolean isAdmin(List<String> roles) {
        return roles.contains("ROLE_ADMIN");
    }

    private boolean isSeller(List<String> roles) {
        return roles.contains("ROLE_SELLER");
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request, Long ownerId, List<String> roles) {
        if (!(isAdmin(roles) || isSeller(roles))) {
            throw new RuntimeException("Unauthorized to create product");
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(request.getCategory())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .ownerId(ownerId)
                .build();

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long productId, UpdateProductRequest request, Long ownerId, List<String> roles) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (!isAdmin(roles) && !product.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("You are not allowed to update this product");
        }

        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getQuantity() != null) product.setQuantity(request.getQuantity());
        if (request.getCategory() != null) product.setCategory(request.getCategory());

        product.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long productId, Long ownerId, List<String> roles) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (!isAdmin(roles) && !product.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("You are not allowed to delete this product");
        }

        productRepository.deleteById(productId);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getMyProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setCategory(product.getCategory());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
}
