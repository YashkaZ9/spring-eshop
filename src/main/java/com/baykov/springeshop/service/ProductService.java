package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.ProductDTO;
import com.baykov.springeshop.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    Product getOne(Long id);
    void addToOrder(Long productId, String username);
    void addToShop(ProductDTO productDTO);
}
