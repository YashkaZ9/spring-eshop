package com.baykov.springeshop.services;

import com.baykov.springeshop.exceptions.OrderException;
import com.baykov.springeshop.models.*;
import com.baykov.springeshop.repos.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepo orderRepo;
    private final CartService cartService;

    @PreAuthorize("hasAuthority('MANAGER')")
    public List<Order> getOrders() {
//        return orderDao.getOrders();
        return orderRepo.findAll();
    }

    @PostAuthorize("hasAuthority('MANAGER') or returnObject.user.email == authentication.principal.username")
    public Order getOrderById(Long id) {
        return orderRepo.findById(id).orElseThrow(() -> new OrderException("This order does not exist."));
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    public List<Order> getOrdersByStatus(OrderStatus status) {
//        return orderDao.getOrdersByStatus(status);
        return orderRepo.findOrdersByOrderStatus(status);
    }

    @PreAuthorize("#user.email == authentication.principal.username or hasAuthority('MANAGER')")
    public List<Order> getOrdersByUser(User user) {
        return orderRepo.findOrderByUserEmail(user.getEmail());
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Order createOrder(User user, String address, String comment) {
        Cart cart = cartService.getCart(user);
        List<CartPosition> cartPositions = cart.getCartPositions();
        if (cartPositions.isEmpty()) {
            throw new OrderException("Cart is empty. Order is not created.");
        }
        Order order = new Order(user);
        order.setOrderPositions(mapCartPositionsToOrderPositions(cartPositions, order));
        order.setTotalSum(cart.getTotalSum());
        order.setAddress(address);
        order.setComment(comment);
        orderRepo.save(order);
        cartService.clearCart(user);
        return order;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public Order manageOrder(Long orderId, Long hoursToExecuteOrder) {
        Order order = getOrderById(orderId);
        if (order.getOrderStatus().equals(OrderStatus.ACTIVE)) {
            throw new OrderException("This order is already managed.");
        }
        order.setPlannedExecutionDate(LocalDateTime.now().plusHours(hoursToExecuteOrder));
        order.setOrderStatus(OrderStatus.ACTIVE);
        return order;
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Order closeOrder(User user, Long orderId) {
        Order order = getOrderById(orderId);
        if (!order.getUser().getEmail().equals(user.getEmail())) {
            throw new OrderException("This user does not have this order.");
        }
        if (order.getOrderStatus().equals(OrderStatus.NEW)) {
            throw new OrderException("This order is not managed yet.");
        }
        if (order.getOrderStatus().equals(OrderStatus.CLOSED)) {
            throw new OrderException("This order is already closed.");
        }
        if (!order.getPlannedExecutionDate().isBefore(LocalDateTime.now())) {
            throw new OrderException("This order is not executed yet.");
        }
        order.setOrderStatus(OrderStatus.CLOSED);
        return order;
    }

    public List<OrderPosition> mapCartPositionsToOrderPositions(List<CartPosition> cartPositions, Order order) {
        return cartPositions.stream().map(cp -> mapCartPositionToOrderPosition(cp, order)).collect(Collectors.toList());
    }

    public OrderPosition mapCartPositionToOrderPosition(CartPosition cartPosition, Order order) {
        return new OrderPosition(order, cartPosition.getProduct().getTitle(), cartPosition.getQuantity(), cartPosition.getSum());
    }
}
