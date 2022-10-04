package com.baykov.springeshop.repos;

import com.baykov.springeshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);

    List<Category> findByTitleContainingIgnoreCase(String query);
}
