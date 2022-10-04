package com.baykov.springeshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "productTitle")
@Entity
@Table(name = "order_positions")
public class OrderPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @Size(min = 2, max = 100, message = "Product title length should be between 2 and 100 symbols.")
    private String productTitle;

    @NotNull(message = "Quantity should be specified.")
    @Min(value = 1, message = "Quantity should be valid.")
    private Long quantity;

    @NotNull(message = "Sum should be specified.")
    @Min(value = 0, message = "Sum should be positive.")
    private BigDecimal sum;

    public OrderPosition(Order order, String productTitle, Long quantity, BigDecimal sum) {
        this.order = order;
        this.productTitle = productTitle;
        this.sum = sum;
        this.quantity = quantity;
    }
}

