package com.amr.ecommerce.order_service.service;

import com.amr.ecommerce.order_service.provider.ProductProvider;
import com.amr.ecommerce.order_service.dto.CreateOrderRequest;
import com.amr.ecommerce.order_service.dto.OrderResponse;
import com.amr.ecommerce.order_service.dto.ProductResponse;
import com.amr.ecommerce.order_service.mapper.OrderMapper;
import com.amr.ecommerce.order_service.model.OrderEntity;
import com.amr.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductProvider productProvider;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request, Long userId, String jwtToken) {
        ProductResponse product = productProvider.getProductById(request.getProductId(), jwtToken);

        if (product == null || request.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("Invalid product or insufficient stock.");
        }

        double totalPrice = request.getQuantity() * product.getPrice();
        OrderEntity entity = orderMapper.toEntity(request, userId, totalPrice);
        orderRepository.save(entity);

        return orderMapper.toResponse(entity);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id, Long userId, boolean isAdmin) {
        var order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if (!isAdmin && !order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to access this order");
        }
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse updateOrder(Long id, CreateOrderRequest request, Long userId, boolean isAdmin) {
        var order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if (!isAdmin && !order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to update this order");
        }

        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public void deleteOrder(Long id, Long userId, boolean isAdmin) {
        var order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if (!isAdmin && !order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this order");
        }
        orderRepository.delete(order);
    }
}
