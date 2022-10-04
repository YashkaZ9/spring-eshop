package com.baykov.springeshop.repos;

import com.baykov.springeshop.models.Order;
import com.baykov.springeshop.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus);

    List<Order> findOrderByUserEmail(String email);
}
