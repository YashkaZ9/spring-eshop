package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.CartDTO;
import com.baykov.springeshop.entity.Cart;
import com.baykov.springeshop.entity.User;

import java.util.List;

public interface CartService {
    Cart createCart(User user, List<Long> productsIds);
    void addProducts(Cart cart, List<Long> productsIds);
    CartDTO getCartByUsername(String username);
    void deleteProduct(Long productId);
}
