package com.baykov.springeshop.controller;

import com.baykov.springeshop.dto.OrderDTO;
import com.baykov.springeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String aboutOrder(Model model, Principal principal) {
        OrderDTO orderDTO = orderService.getOrderByUsername(principal.getName());
        model.addAttribute("order", orderDTO);
        return "order";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{productId}")
    public String removeFromOrder(@PathVariable Long productId, Principal principal) {
        orderService.deleteProductFromOrder(productId, principal.getName());
        return "redirect:/order";
    }
}
