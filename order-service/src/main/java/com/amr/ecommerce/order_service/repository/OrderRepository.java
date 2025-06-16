package com.amr.ecommerce.order_service.repository;

import com.amr.ecommerce.order_service.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
}
