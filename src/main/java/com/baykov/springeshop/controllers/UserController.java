package com.baykov.springeshop.controllers;

import com.baykov.springeshop.dtos.UserDto;
import com.baykov.springeshop.mappers.UserMapper;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return userMapper.fromUsers(users);
    }
}
