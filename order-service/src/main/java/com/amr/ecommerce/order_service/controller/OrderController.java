package com.amr.ecommerce.order_service.controller;


import com.amr.ecommerce.order_service.dto.CreateOrderRequest;
import com.amr.ecommerce.order_service.dto.OrderResponse;
import com.amr.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.parseLong(jwt.getSubject());
        return ResponseEntity.ok(orderService.createOrder(request, userId));
    }
}

