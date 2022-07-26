package com.baykov.springeshop.service;

import com.baykov.springeshop.dto.UserDTO;
import com.baykov.springeshop.entity.Order;
import com.baykov.springeshop.entity.Role;
import com.baykov.springeshop.entity.User;
import com.baykov.springeshop.repo.OrderRepo;
import com.baykov.springeshop.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final OrderRepo orderRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, OrderRepo orderRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.orderRepo = orderRepo;
    }

    @Override
    @Transactional
    public boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getConfirmPassword())) {
            throw new RuntimeException("Passwords are different.");
        }
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.USER)
                .build();
        userRepo.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateProfile(UserDTO userDTO) {
        User user = userRepo.findByUsername(userDTO.getUsername());
        if (user == null) {
            throw new RuntimeException("The user '" + userDTO.getUsername() + "' is not found.");
        }
        boolean profileWasChanged = false;
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            profileWasChanged = true;
        }
        if (!Objects.equals(userDTO.getEmail(), user.getEmail())) {
            user.setEmail(userDTO.getEmail());
            profileWasChanged = true;
        }
        if (profileWasChanged) {
            userRepo.save(user);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public void addOrder(Order order) {
        orderRepo.save(order);
    }

    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' is not found.");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), roles
        );
    }
}
