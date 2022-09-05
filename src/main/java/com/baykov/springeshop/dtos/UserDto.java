package com.baykov.springeshop.dtos;

import com.baykov.springeshop.models.Role;
import com.baykov.springeshop.models.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Size(max = 100, message = "Email length should be between 2 and 100 symbols.")
    @Email(message = "Email should be valid.")
    private String email;

    @Size(max = 100, message = "Password length should be between 2 and 100 symbols.")
    private String password;

    @Size(max = 100, message = "Name length should be not more than 100 symbols.")
    private String name;

    @Size(max = 100, message = "Surname length should be not more than 100 symbols.")
    private String surname;

    @Size(max = 100, message = "Phone number should be valid.")
    private String phone;

    @NotNull(message = "Role should be specified.")
    private Role role;

    @NotNull(message = "User status should be specified.")
    private UserStatus status;

    public UserDto(AuthDto authDto) {
        this.email = authDto.getEmail();
        this.password = authDto.getPassword();
    }
}
