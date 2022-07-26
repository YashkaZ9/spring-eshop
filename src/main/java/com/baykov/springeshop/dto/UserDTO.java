package com.baykov.springeshop.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @NotEmpty(message = "Username must not be empty.")
    @Max(value = 30, message = "Username must be not more than 30 symbols.")
    private String username;
    @NotEmpty(message = "Password must not be empty.")
    @Min(value = 5, message = "Password must be not less than 5 symbols.")
    private String password;
    @NotEmpty(message = "Password must not be empty.")
    @Min(value = 5, message = "Password must be not less than 5 symbols.")
    private String confirmPassword;
    @NotEmpty(message = "Email must not be empty.")
    @Email(message = "Email must be valid.")
    private String email;
}
