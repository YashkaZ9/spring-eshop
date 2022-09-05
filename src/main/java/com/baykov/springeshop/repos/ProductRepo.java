package com.baykov.springeshop.repos;

import com.baykov.springeshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByTitle(String title);

    Set<Product> findByTitleContainingIgnoreCase(String query);
}
