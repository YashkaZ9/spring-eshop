package com.baykov.springeshop.repo;

import com.baykov.springeshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
}
