package com.baykov.springeshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartPositionDto {
    @NotNull(message = "Product should be specified.")
    private ProductDto product;

    @NotNull(message = "Quantity should be specified.")
    private Long quantity;

    @NotNull(message = "Sum should be specified.")
    @Min(value = 0, message = "Sum should be positive.")
    private BigDecimal sum;
}
