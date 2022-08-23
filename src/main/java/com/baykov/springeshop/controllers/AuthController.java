package com.baykov.springeshop.controllers;

import com.baykov.springeshop.dtos.UserDto;
import com.baykov.springeshop.exceptions.ExceptionMessageBuilder;
import com.baykov.springeshop.exceptions.PersonNotCreatedException;
import com.baykov.springeshop.mappers.UserMapper;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.services.UserService;
import com.baykov.springeshop.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final UserService userService;

    @Autowired
    public AuthController(UserValidator userValidator, UserMapper userMapper, UserService userService) {
        this.userValidator = userValidator;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping("/register")
    public HttpStatus addUser(@RequestBody @Valid UserDto userDto,
                                     BindingResult errors) {
        userValidator.validate(userDto, errors);
        if (errors.hasErrors()) {
            String errorMsg = ExceptionMessageBuilder.buildErrorMessage(errors);
            throw new PersonNotCreatedException(errorMsg);
        }
        User user = userMapper.toUser(userDto);
        userService.saveUser(user);
        return HttpStatus.ACCEPTED;
    }
}
