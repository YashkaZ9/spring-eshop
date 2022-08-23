package com.baykov.springeshop.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Category should have a title.")
    @Size(min = 2, max = 100, message = "Category title length should be between 2 and 100 symbols.")
    private String title;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;
}
