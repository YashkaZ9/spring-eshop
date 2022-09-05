package com.baykov.springeshop.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "productTitle")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_positions")
public class OrderPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Size(min = 2, max = 100, message = "Product title length should be between 2 and 100 symbols.")
    @Column(name = "product_title")
    private String productTitle;

    @NotNull(message = "Quantity should be specified.")
    @Min(value = 1, message = "Quantity should be valid.")
    @Column(name = "quantity")
    private Long quantity;

    @NotNull(message = "Sum should be specified.")
    @Min(value = 0, message = "Sum should be positive.")
    @Column(name = "sum")
    private BigDecimal sum;

    public OrderPosition(Order order, String productTitle, Long quantity, BigDecimal sum) {
        this.order = order;
        this.productTitle = productTitle;
        this.sum = sum;
        this.quantity = quantity;
    }
}
