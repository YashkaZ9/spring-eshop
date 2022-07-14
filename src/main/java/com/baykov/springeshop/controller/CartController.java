package com.baykov.springeshop.controller;

import com.baykov.springeshop.dto.CartDTO;
import com.baykov.springeshop.service.CartService;
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
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String aboutCart(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("cart", new CartDTO());
        } else {
            CartDTO cartDTO = cartService.getCartByUsername(principal.getName());
            model.addAttribute("cart", cartDTO);
        }
        return "cart";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        cartService.deleteProduct(productId);
        return "redirect:/cart";
    }
}
