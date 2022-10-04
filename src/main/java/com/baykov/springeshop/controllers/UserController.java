package com.baykov.springeshop.controllers;

import com.baykov.springeshop.dtos.UserDto;
import com.baykov.springeshop.utils.ExceptionUtil;
import com.baykov.springeshop.exceptions.UserException;
import com.baykov.springeshop.mappers.UserMapper;
import com.baykov.springeshop.models.User;
import com.baykov.springeshop.models.UserStatus;
import com.baykov.springeshop.services.UserService;
import com.baykov.springeshop.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(userMapper.fromUsers(users), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(userMapper.fromUser(user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody UserDto userDto,
                                      BindingResult errors) throws Exception {
        User user = userService.getUserById(id);
        userValidator.validate(userDto, errors);
        ExceptionUtil.checkExceptions(errors, UserException.class);
        User updatedUser = userMapper.toUser(userDto);
        updatedUser = userService.editUser(user, updatedUser);
        return new ResponseEntity<>(userMapper.fromUser(updatedUser), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return HttpStatus.OK;
    }

    @PatchMapping("/{id}/set-manager")
    public HttpStatus setManager(@PathVariable Long id) {
        userService.setManager(id);
        return HttpStatus.ACCEPTED;
    }

    @PatchMapping("/{id}/ban")
    public HttpStatus banUser(@PathVariable Long id) {
        userService.changeUserStatus(id, UserStatus.BANNED);
        return HttpStatus.ACCEPTED;
    }

    @PatchMapping("/{id}/activate")
    public HttpStatus activateUser(@PathVariable Long id) {
        userService.changeUserStatus(id, UserStatus.ACTIVE);
        return HttpStatus.ACCEPTED;
    }
}
