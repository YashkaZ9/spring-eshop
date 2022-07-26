package com.baykov.springeshop.controller;

import com.baykov.springeshop.dto.ProductDTO;
import com.baykov.springeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping
    public String getAllProducts(@ModelAttribute(name = "productSpec") ProductDTO productDTO, Model model) {
        List<ProductDTO> products = productService.getAll();
        model.addAttribute("products", products);
        return "productsList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public String addToOrder(@PathVariable Long id, Principal principal) {
        productService.addToOrder(id, principal.getName());
        return "redirect:/products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String addProduct(@ModelAttribute(name = "productSpec") @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/products";
        }
        productService.addToShop(productDTO);
        return "redirect:/products";
    }
}
