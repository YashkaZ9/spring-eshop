package com.baykov.springeshop.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @Size(min = 2, max = 100, message = "Product title length should be between 2 and 100 symbols.")
    private String title;

    @Column(name = "price")
    @NotNull(message = "Price should be specified.")
    @Min(value = 0, message = "Price should be positive.")
    private BigDecimal price;

    @Column(name = "description")
    @Size(max = 255, message = "Description should be shorter.")
    private String description;

    @Column(name = "image")
    @Lob
    private byte[] image;

    @ManyToMany
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;
}
