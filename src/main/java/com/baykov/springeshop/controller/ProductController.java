package com.baykov.springeshop.controller;

import com.baykov.springeshop.dto.ProductDTO;
import com.baykov.springeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping
    public String getAllProducts(Model model) {
        List<ProductDTO> products = productService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("productSpec", new ProductDTO());
        return "productsList";
    }

    @GetMapping("/{id}/cart")
    public String addToCart(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/products";
        }
        productService.addToUserCart(id, principal.getName());
        return "redirect:/products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String addProduct(ProductDTO productDTO) {
        productService.addProduct(productDTO);
        return "redirect:/products";
    }
}
