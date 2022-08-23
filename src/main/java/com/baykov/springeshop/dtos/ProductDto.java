package com.baykov.springeshop.dtos;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @Size(min = 2, max = 100, message = "Product title length should be between 2 and 100 symbols.")
    private String title;

    @NotNull(message = "Price should be specified.")
    @Min(value = 0, message = "Price should be positive.")
    private BigDecimal price;

    @Size(max = 255, message = "Description should be shorter.")
    private String description;

    @Lob
    private byte[] image;
}
