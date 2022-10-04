package com.baykov.springeshop.repos;

import com.baykov.springeshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
    Optional<Product> findByTitle(String title);

    List<Product> findByTitleContainingIgnoreCase(String query);
}
