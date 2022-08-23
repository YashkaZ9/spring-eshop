package com.baykov.springeshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Size(min = 2, max = 100, message = "Email length should be between 2 and 100 symbols.")
    @Email(message = "Email should be valid.")
    private String email;

    @Size(min = 2, max = 100, message = "Password length should be between 2 and 100 symbols.")
    private String password;

    @Size(max = 100, message = "Name length should be not more than 100 symbols.")
    private String name;

    @Size(max = 100, message = "Surname length should be not more than 100 symbols.")
    private String surname;

    @Size(max = 100, message = "Phone number should be valid.")
    private String phone;
}
