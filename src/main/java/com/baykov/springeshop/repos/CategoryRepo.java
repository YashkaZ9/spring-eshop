package com.baykov.springeshop.repos;

import com.baykov.springeshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);

    Set<Category> findByTitleContainingIgnoreCase(String query);
}
