package com.amr.ecommerce.order_service.service;

import com.amr.ecommerce.order_service.dto.CreateOrderRequest;
import com.amr.ecommerce.order_service.dto.OrderResponse;
import com.amr.ecommerce.order_service.dto.ProductResponse;
import com.amr.ecommerce.order_service.mapper.OrderMapper;
import com.amr.ecommerce.order_service.model.OrderEntity;
import com.amr.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.*;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final OrderMapper orderMapper;

    @Value("${service.product.url}")
    private String productServiceUrl;

    public OrderResponse createOrder(CreateOrderRequest request, Long userId) {
        // Call product-service to validate product existence and price
        ProductResponse product = webClientBuilder.build()
                .get()
                .uri(productServiceUrl + "/api/products/{id}", request.getProductId())
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();

        if (product == null || request.getQuantity() > product.getAvailableQuantity()) {
            throw new RuntimeException("Invalid product or insufficient stock.");
        }

        double totalPrice = request.getQuantity() * product.getPrice();

        OrderEntity entity = orderMapper.toEntity(request, userId, totalPrice);
        orderRepository.save(entity);

        return orderMapper.toResponse(entity);
    }
}
