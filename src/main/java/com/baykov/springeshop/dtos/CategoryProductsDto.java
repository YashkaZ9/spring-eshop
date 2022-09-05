package com.baykov.springeshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductsDto {
    @Size(min = 2, max = 100, message = "Category title length should be between 2 and 100 symbols.")
    private String title;

    private Set<ProductDto> products;
}