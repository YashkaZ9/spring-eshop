package com.baykov.springeshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Set<CartPositionDto> cartPositions;

    @NotNull(message = "Total sum should be specified.")
    @Min(value = 0, message = "Total sum should be positive.")
    private BigDecimal totalSum;
}
