package com.amr.ecommerce.order_service.controller;


import com.amr.ecommerce.order_service.dto.CreateOrderRequest;
import com.amr.ecommerce.order_service.dto.OrderResponse;
import com.amr.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SELLER', 'ROLE_USER')")
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("userId");
        return ResponseEntity.ok(orderService.createOrder(request, userId, jwt.getTokenValue()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'USER')")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        boolean isAdmin = jwt.getClaimAsStringList("roles").contains("ROLE_ADMIN");
        return ResponseEntity.ok(orderService.getOrderById(id, userId, isAdmin));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody CreateOrderRequest request,
                                                     @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        boolean isAdmin = jwt.getClaimAsStringList("roles").contains("ROLE_ADMIN");
        return ResponseEntity.ok(orderService.updateOrder(id, request, userId, isAdmin));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        boolean isAdmin = jwt.getClaimAsStringList("roles").contains("ROLE_ADMIN");
        orderService.deleteOrder(id, userId, isAdmin);
        return ResponseEntity.noContent().build();
    }
}
