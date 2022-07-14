package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserCart(Long productId, String username);
    void addProduct(ProductDTO productDTO);
}
