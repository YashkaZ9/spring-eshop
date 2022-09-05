package com.baykov.springeshop.controllers;

import com.baykov.springeshop.dtos.OrderInfoDto;
import com.baykov.springeshop.exceptions.ExceptionUtil;
import com.baykov.springeshop.exceptions.OrderException;
import com.baykov.springeshop.mappers.OrderMapper;
import com.baykov.springeshop.models.Order;
import com.baykov.springeshop.models.OrderStatus;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.services.OrderService;
import com.baykov.springeshop.services.UserService;
import com.baykov.springeshop.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderValidator orderValidator;
    private final UserService userService;

    @GetMapping("/orders")
    public ResponseEntity<?> getOrders() {
        Set<Order> orders = orderService.getOrders();
        return new ResponseEntity<>(orderMapper.fromOrders(orders), HttpStatus.OK);
    }

    @GetMapping("/orders/new")
    public ResponseEntity<?> getNewOrders() {
        Set<Order> orders = orderService.getOrdersByStatus(OrderStatus.NEW);
        return new ResponseEntity<>(orderMapper.fromOrders(orders), HttpStatus.OK);
    }

    @GetMapping("/orders/active")
    public ResponseEntity<?> getActiveOrders() {
        Set<Order> orders = orderService.getOrdersByStatus(OrderStatus.ACTIVE);
        return new ResponseEntity<>(orderMapper.fromOrders(orders), HttpStatus.OK);
    }

    @GetMapping("/orders/closed")
    public ResponseEntity<?> getClosedOrders() {
        Set<Order> orders = orderService.getOrdersByStatus(OrderStatus.CLOSED);
        return new ResponseEntity<>(orderMapper.fromOrders(orders), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<?> getOrdersByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Set<Order> orders = orderService.getOrdersByUser(user);
        return new ResponseEntity<>(orderMapper.fromOrders(orders), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> makeOrder(@PathVariable Long userId, @RequestBody @Valid OrderInfoDto orderInfoDto,
                                       BindingResult errors) throws Exception {
        User user = userService.getUserById(userId);
        orderValidator.validate(orderInfoDto, errors);
        ExceptionUtil.checkExceptions(errors, OrderException.class);
        Order order = orderService.createOrder(user, orderInfoDto.getAddress(), orderInfoDto.getComment());
        return new ResponseEntity<>(orderMapper.fromOrder(order), HttpStatus.CREATED);
    }

    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<?> manageOrder(@PathVariable Long orderId,
                                         @RequestParam(value = "hours_to_execute_order") Long hoursToExecuteOrder) {
        Order order = orderService.manageOrder(orderId, hoursToExecuteOrder);
        return new ResponseEntity<>(orderMapper.fromOrder(order), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/users/{userId}/orders/{orderId}")
    public ResponseEntity<?> closeOrder(@PathVariable(value = "userId") Long userId,
                                        @PathVariable(value = "orderId") Long orderId) {
        User user = userService.getUserById(userId);
        Order order = orderService.closeOrder(user, orderId);
        return new ResponseEntity<>(orderMapper.fromOrder(order), HttpStatus.ACCEPTED);
    }
}
