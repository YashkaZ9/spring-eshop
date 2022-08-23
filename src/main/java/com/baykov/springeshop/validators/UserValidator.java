package com.baykov.springeshop.validators;

import com.baykov.springeshop.dtos.UserDto;
import com.baykov.springeshop.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserRepo userRepo;

    @Autowired
    public UserValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "User with this email already exists.");
        }
    }
}
