package com.amrfawzi.ecommerce.product_service.controller;

import com.amrfawzi.ecommerce.product_service.dto.CreateProductRequest;
import com.amrfawzi.ecommerce.product_service.dto.ProductResponse;
import com.amrfawzi.ecommerce.product_service.dto.UpdateProductRequest;
import com.amrfawzi.ecommerce.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request,
                                                         @AuthenticationPrincipal Jwt jwt) {
        Long ownerId = jwt.getClaim("userId");
        List<String> roles = jwt.getClaimAsStringList("roles");

        return new ResponseEntity<>(productService.createProduct(request, ownerId, roles), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateProductRequest request,
                                                         @AuthenticationPrincipal Jwt jwt) {
        Long ownerId = jwt.getClaim("userId");
        List<String> roles = jwt.getClaimAsStringList("roles");

        return ResponseEntity.ok(productService.updateProduct(id, request, ownerId, roles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long ownerId = jwt.getClaim("userId");
        List<String> roles = jwt.getClaimAsStringList("roles");

        productService.deleteProduct(id, ownerId, roles);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProductResponse>> getMyProducts(@AuthenticationPrincipal Jwt jwt) {
        //Long ownerId = jwt.getClaim("userId");
        return ResponseEntity.ok(productService.getMyProducts());
    }
}
