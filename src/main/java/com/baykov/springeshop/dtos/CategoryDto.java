package com.baykov.springeshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotNull(message = "Product id should be specified.")
    private Long id;

    @NotEmpty(message = "Category should have a title.")
    @Size(min = 2, max = 100, message = "Category title length should be between 2 and 100 symbols.")
    private String title;
}
