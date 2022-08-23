package com.baykov.springeshop.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    @Size(min = 2, max = 100, message = "Email length should be between 2 and 100 symbols.")
    @Email(message = "Email should be valid.")
    private String email;

    @Column(name = "password")
    @Size(min = 2, max = 100, message = "Password length should be between 2 and 100 symbols.")
    private String password;

    @Column(name = "name")
    @Size(max = 100, message = "Name length should be not more than 100 symbols.")
    private String name;

    @Column(name = "surname")
    @Size(max = 100, message = "Surname length should be not more than 100 symbols.")
    private String surname;

    @Column(name = "phone")
    @Size(max = 100, message = "Phone number should be valid.")
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;
}
