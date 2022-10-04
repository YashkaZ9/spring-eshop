package com.baykov.springeshop.dtos;

import com.baykov.springeshop.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    @NotNull(message = "Order id should be specified.")
    private Long id;

    private List<OrderPositionDto> orderPositions;

    @NotNull(message = "Total sum should be specified.")
    @Min(value = 0, message = "Total sum should be positive.")
    private BigDecimal totalSum;

    @NotNull(message = "Order status should be specified.")
    private OrderStatus orderStatus;

    @NotNull(message = "Order creation time should be specified.")
    private LocalDateTime createdAt;

    @NotNull(message = "Order update time should be specified.")
    private LocalDateTime updatedAt;

    private LocalDateTime plannedExecutionDate;

    @Pattern(regexp = "[A-ZА-Я][A-Za-zА-Яа-я-.'\\s]*, [A-ZА-Я][A-Za-zА-Яа-я-.'\\s]*, \\d{6}",
            message = "Address should match the pattern: Country, City, Index (6 numbers)")
    private String address;

    @Size(max = 255, message = "Comment length should be not more than 255 symbols.")
    private String comment;
}
