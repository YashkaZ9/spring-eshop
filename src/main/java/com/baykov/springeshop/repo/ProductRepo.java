package com.baykov.springeshop.repo;

import com.baykov.springeshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByTitle(String title);
}
