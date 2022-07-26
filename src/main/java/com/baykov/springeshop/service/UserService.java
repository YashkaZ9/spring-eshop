package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.UserDTO;
import com.baykov.springeshop.entity.Order;
import com.baykov.springeshop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO personDTO);
    void save(User user);
    List<UserDTO> getAll();
    void updateProfile(UserDTO userDTO);
    User findByUsername(String username);
    void addOrder(Order order);
}
