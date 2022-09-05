package com.baykov.springeshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    @Size(min = 2, max = 100, message = "Email length should be between 2 and 100 symbols.")
    @Email(message = "Email should be valid.")
    private String email;

    @Size(min = 2, max = 100, message = "Password length should be between 2 and 100 symbols.")
    private String password;
}