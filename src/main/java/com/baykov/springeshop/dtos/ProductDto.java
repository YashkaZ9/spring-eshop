package com.baykov.springeshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotNull(message = "Product id should be specified.")
    private Long id;

    @Size(min = 2, max = 100, message = "Title length should be between 2 and 100 symbols.")
    private String title;

    @NotNull(message = "Price should be specified.")
    @Min(value = 0, message = "Price should be positive.")
    private BigDecimal price;

    @Size(max = 255, message = "Description should be shorter.")
    private String description;
}
