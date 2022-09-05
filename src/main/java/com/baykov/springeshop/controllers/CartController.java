package com.baykov.springeshop.controllers;

import com.baykov.springeshop.mappers.CartMapper;
import com.baykov.springeshop.models.Cart;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.services.CartService;
import com.baykov.springeshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;
    private final UserService userService;

    @GetMapping("/{userId}/cart")
    public ResponseEntity<?> getCart(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Cart cart = cartService.getCart(user);
        return new ResponseEntity<>(cartMapper.fromCart(cart), HttpStatus.OK);
    }

    @PutMapping("/{userId}/cart/add/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable(value = "userId") Long userId,
                                              @PathVariable(value = "productId") Long productId) {
        User user = userService.getUserById(userId);
        Cart cart = cartService.addProduct(user, productId);
        return new ResponseEntity<>(cartMapper.fromCart(cart), HttpStatus.OK);
    }

    @PutMapping("/{userId}/cart/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable(value = "userId") Long userId,
                                                   @PathVariable(value = "productId") Long productId) {
        User user = userService.getUserById(userId);
        Cart cart = cartService.removeProduct(user, productId);
        return new ResponseEntity<>(cartMapper.fromCart(cart), HttpStatus.OK);
    }

    @PutMapping("/{userId}/cart/remove-position/{productId}")
    public ResponseEntity<?> removePositionFromCart(@PathVariable(value = "userId") Long userId,
                                                    @PathVariable(value = "productId") Long productId) {
        User user = userService.getUserById(userId);
        Cart cart = cartService.removePosition(user, productId);
        return new ResponseEntity<>(cartMapper.fromCart(cart), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/cart")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Cart cart = cartService.clearCart(user);
        return new ResponseEntity<>(cartMapper.fromCart(cart), HttpStatus.OK);
    }
}
