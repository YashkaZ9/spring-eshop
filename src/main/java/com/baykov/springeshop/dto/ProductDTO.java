package com.baykov.springeshop.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    @NotEmpty(message = "Title must not be empty.")
    @Size(min = 2, max = 50, message = "Title must be between 2 and 50 symbols.")
    private String title;
    @NotEmpty(message = "Price must not be empty.")
    private BigDecimal price;
}
