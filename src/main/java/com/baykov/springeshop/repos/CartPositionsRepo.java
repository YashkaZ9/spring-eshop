package com.baykov.springeshop.repos;

import com.baykov.springeshop.models.CartPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartPositionsRepo extends JpaRepository<CartPosition, Long> {
}
