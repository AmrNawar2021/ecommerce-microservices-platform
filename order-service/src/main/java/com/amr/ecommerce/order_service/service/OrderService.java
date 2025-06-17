package com.amr.ecommerce.order_service.service;

import com.amr.ecommerce.order_service.dto.CreateOrderRequest;
import com.amr.ecommerce.order_service.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request, Long userId, String jwtToken);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id, Long userId, boolean isAdmin);

    OrderResponse updateOrder(Long id, CreateOrderRequest request, Long userId, boolean isAdmin);

    void deleteOrder(Long id, Long userId, boolean isAdmin);
}
