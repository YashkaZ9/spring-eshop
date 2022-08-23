package com.baykov.springeshop.repos;

import com.baykov.springeshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByTitle(String title);
}
