package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.OrderDTO;
import com.baykov.springeshop.entity.Order;
import com.baykov.springeshop.entity.Product;
import com.baykov.springeshop.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, List<Long> productsIds);
    void addProducts(List<Product> products);
    OrderDTO getOrderByUsername(String username);
    Order getOrderByCartId(Long cartId);
    void deleteProductFromOrder(Long productId, String username);
    Order getOne(Long id);
}
