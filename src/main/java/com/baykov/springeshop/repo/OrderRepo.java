package com.baykov.springeshop.repo;

import com.baykov.springeshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
    Order getOrderById(Long orderId);
}
