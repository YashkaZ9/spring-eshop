package com.baykov.springeshop.services;

import com.baykov.springeshop.exceptions.CartException;
import com.baykov.springeshop.models.Cart;
import com.baykov.springeshop.models.CartPosition;
import com.baykov.springeshop.models.Product;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.repos.CartPositionsRepo;
import com.baykov.springeshop.repos.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepo cartRepo;
    private final ProductService productService;
    private final CartPositionsRepo cartPositionsRepo;

    @PreAuthorize("hasAuthority('MANAGER')")
    public List<Cart> getCarts() {
        return cartRepo.findAll();
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Cart getCart(User user) {
        Cart cart = cartRepo.findCartByUserId(user.getId()).orElse(null);
        if (cart == null) {
            cart = createCart(user);
        }
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
        Cart cart = getCart(user);
        Product product = productService.getProductById(productId);
        CartPosition cartPosition = new CartPosition(product, cart);
        cart.getCartPositions().add(cartPosition);
        cartPosition = getCartPositionByProduct(cart, product);
        cartPosition.setQuantity(cartPosition.getQuantity() + 1);
        cartPosition.setSum(cartPosition.getSum().add(product.getPrice()));
        cart.setTotalSum(cart.getTotalSum().add(product.getPrice()));
        return cart;
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Cart removeProduct(User user, Long productId) {
        Cart cart = getCart(user);
        Product product = productService.getProductById(productId);
        CartPosition cartPosition = getCartPositionByProduct(cart, product);
        Long productQuantity = cartPosition.getQuantity();
        if (productQuantity.compareTo(1L) > 0) {
            cartPosition.setQuantity(productQuantity - 1);
            cartPosition.setSum(cartPosition.getSum().subtract(product.getPrice()));
        } else {
            cart.getCartPositions().remove(cartPosition);
        }
        cart.setTotalSum(cart.getTotalSum().subtract(product.getPrice()));
        return cart;
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Cart removePosition(User user, Long productId) {
        Cart cart = getCart(user);
        Product product = productService.getProductById(productId);
        CartPosition cartPosition = getCartPositionByProduct(cart, product);
        cart.getCartPositions().remove(cartPosition);
        cart.setTotalSum(cart.getTotalSum().subtract(cartPosition.getSum()));
        return cart;
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public Cart clearCart(User user) {
        Cart cart = getCart(user);
        cart.getCartPositions().clear();
        cart.setTotalSum(BigDecimal.ZERO);
        return cart;
    }

    @PreAuthorize("#cart.user.email == authentication.principal.username")
    public CartPosition getCartPositionByProduct(Cart cart, Product product) {
        return cart.getCartPositions().stream().filter(cp -> cp.getProduct().equals(product)).findAny()
                .orElseThrow(() -> new CartException("This cart position does not exist."));
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Transactional
    public void refreshCarts(Product product, BigDecimal price) {
        Long productId = product.getId();
        cartPositionsRepo.findAll().stream()
                .filter(cp -> cp.getProduct().getId().equals(productId))
                .forEach(cp -> cp.setSum(price.multiply(BigDecimal.valueOf(cp.getQuantity()))));
        CartPosition cartPosition = new CartPosition(product);
        getCarts().stream()
                .filter(c -> c.getCartPositions().contains(cartPosition))
                .forEach(c -> c.setTotalSum(c.getCartPositions().stream()
                        .map(CartPosition::getSum)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
    }
}
