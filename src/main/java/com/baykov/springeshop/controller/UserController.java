package com.baykov.springeshop.controller;

import com.baykov.springeshop.entity.User;
import com.baykov.springeshop.dto.UserDTO;
import com.baykov.springeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String usersList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "usersList";
    }

    @PostAuthorize("isAuthenticated() and #username == authentication.principal.username")
    @ResponseBody
    @GetMapping("/{username}/roles")
    public String userRoles(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return user != null ? user.getRole().name() : "User does not exist.";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") UserDTO userDTO, Model model) {
        return "user";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("person") @Valid UserDTO userDTO,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/users";
        }
        if (userService.save(userDTO)) {
            return "redirect:/users";
        }
        model.addAttribute("user", userDTO);
        return "user";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You're not authorized");
        }
        User user = userService.findByUsername(principal.getName());
        UserDTO userDTO = UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", userDTO);
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile")
    public String updateUserProfile(@Valid UserDTO userDTO, BindingResult bindingResult,
                                    Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), userDTO.getUsername())) {
            throw new RuntimeException("You're not authorized.");
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()
                && !Objects.equals(userDTO.getPassword(), userDTO.getConfirmPassword())) {
            model.addAttribute("user", userDTO);
            return "profile";
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/users/profile";
        }
        userService.updateProfile(userDTO);
        return "redirect:/users/profile";
    }
}
