package com.baykov.springeshop.controllers;

import com.baykov.springeshop.dtos.AuthDto;
import com.baykov.springeshop.dtos.UserDto;
import com.baykov.springeshop.exceptions.ExceptionUtil;
import com.baykov.springeshop.exceptions.UserException;
import com.baykov.springeshop.mappers.UserMapper;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.services.UserService;
import com.baykov.springeshop.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody @Valid AuthDto authDto, BindingResult errors) throws Throwable {
        UserDto userDto = new UserDto(authDto);
        userValidator.validate(userDto, errors);
        ExceptionUtil.checkExceptions(errors, UserException.class);
        User user = userMapper.toUser(userDto);
        userService.saveUser(user);
        return new ResponseEntity<>(userMapper.fromUser(user), HttpStatus.CREATED);
    }
}
