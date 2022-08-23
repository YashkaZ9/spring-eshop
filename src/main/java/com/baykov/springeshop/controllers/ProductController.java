package com.baykov.springeshop.controllers;

import com.baykov.springeshop.dtos.ProductDto;
import com.baykov.springeshop.mappers.ProductMapper;
import com.baykov.springeshop.models.Product;
import com.baykov.springeshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDto> getProducts() {
        List<Product> products = productService.getProducts();
        return productMapper.fromProducts(products);
    }
}
