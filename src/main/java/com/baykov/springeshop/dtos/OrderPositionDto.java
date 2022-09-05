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
public class OrderPositionDto {
    @Size(min = 2, max = 100, message = "Product title length should be between 2 and 100 symbols.")
    private String productTitle;

    @NotNull(message = "Quantity should be specified.")
    @Min(value = 1, message = "Quantity should be valid.")
    private Long quantity;

    @NotNull(message = "Sum should be specified.")
    @Min(value = 0, message = "Sum should be positive.")
    private BigDecimal sum;
}
