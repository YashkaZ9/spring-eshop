package com.baykov.springeshop.services;

import com.baykov.springeshop.exceptions.CartException;
import com.baykov.springeshop.models.Cart;
import com.baykov.springeshop.models.CartPosition;
import com.baykov.springeshop.models.Product;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.repos.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepo cartRepo;
    private final ProductService productService;

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Cart getCart(User user) {
        Cart cart = cartRepo.findCartByUserEmail(user.getEmail()).orElse(null);
        if (cart == null) {
            cart = createCart(user);
        }
        refreshCartTotalSum(cart);
        return cart;
    }

    @Transactional
    public Cart createCart(User user) {
        Cart cart = new Cart(user);
        cartRepo.save(cart);
        return cart;
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Cart addProduct(User user, Long productId) {
        Product product = productService.getProductById(productId);
        Cart cart = getCart(user);
        CartPosition cartPosition = new CartPosition(product, cart);
        if (!cart.getCartPositions().contains(cartPosition)) {
            cart.getCartPositions().add(cartPosition);
        }
        cartPosition = getCartPositionByProduct(cart, product);
        cartPosition.setQuantity(cartPosition.getQuantity() + 1);
        refreshCartTotalSum(cart);
        return cart;
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Cart removeProduct(User user, Long productId) {
        Product product = productService.getProductById(productId);
        Cart cart = getCart(user);
        CartPosition cartPosition = getCartPositionByProduct(cart, product);
        Long productQuantity = cartPosition.getQuantity();
        if (productQuantity.compareTo(1L) > 0) {
            cartPosition.setQuantity(productQuantity - 1);
        } else {
            cart.getCartPositions().remove(cartPosition);
        }
        refreshCartTotalSum(cart);
        return cart;
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Cart removePosition(User user, Long productId) {
        Product product = productService.getProductById(productId);
        Cart cart = getCart(user);
        CartPosition cartPosition = getCartPositionByProduct(cart, product);
        cart.getCartPositions().remove(cartPosition);
        refreshCartTotalSum(cart);
        return cart;
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Cart clearCart(User user) {
        Cart cart = getCart(user);
        cart.getCartPositions().clear();
        refreshCartTotalSum(cart);
        return cart;
    }

    @PreAuthorize("#cart.user.email == authentication.principal.username")
    public CartPosition getCartPositionByProduct(Cart cart, Product product) {
        return cart.getCartPositions().stream().filter(cp -> cp.getProduct().equals(product)).findAny()
                .orElseThrow(() -> new CartException("This cart position does not exist."));
    }

    @PreAuthorize("#cart.user.email == authentication.principal.username or hasAuthority('MANAGER')")
    private void refreshCartTotalSum(Cart cart) {
        cart.getCartPositions().forEach(cp -> cp.setSum(cp.getProduct().getPrice().multiply(BigDecimal.valueOf(cp.getQuantity()))));
        cart.setTotalSum(cart.getCartPositions().stream().map(CartPosition::getSum).reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
