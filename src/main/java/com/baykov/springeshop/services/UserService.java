package com.baykov.springeshop.services;

import com.baykov.springeshop.models.Role;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.models.UserStatus;
import com.baykov.springeshop.repos.UserRepo;
import com.baykov.springeshop.security.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PostAuthorize;
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
    private final CartService cartService;

    @Autowired
    public UserService(UserRepo userRepo, @Lazy PasswordEncoder passwordEncoder, @Lazy CartService cartService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @PostAuthorize("hasAnyAuthority('ADMIN', 'MANAGER') or returnObject.email == authentication.principal.username")
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("This user does not exist."));
    }

    @PreAuthorize("isAnonymous()")
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
        userRepo.save(user);
        cartService.createCart(user);
    }

    @PreAuthorize("#user.email == authentication.principal.username")
    @Transactional
    public User editUser(User user, User updatedUser) {
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        user.getPersonalInfo().setName(updatedUser.getPersonalInfo().getName());
        user.getPersonalInfo().setSurname(updatedUser.getPersonalInfo().getSurname());
        user.getPersonalInfo().setPhone(updatedUser.getPersonalInfo().getPhone());
        return user;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void setManager(Long id) {
        User user = getUserById(id);
        user.setRole(Role.MANAGER);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void changeUserStatus(Long id, UserStatus status) {
        User user = getUserById(id);
        user.setStatus(status);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("This user does not exist."));
        return AuthUser.authUser(user);
    }
}
