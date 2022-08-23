package com.baykov.springeshop.services;

import com.baykov.springeshop.models.Cart;
import com.baykov.springeshop.models.Role;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.models.UserStatus;
import com.baykov.springeshop.repos.UserRepo;
import com.baykov.springeshop.security.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setCart(new Cart(user));
        userRepo.save(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("This user does not exist."));
        return AuthUser.authUser(user);
    }
}
