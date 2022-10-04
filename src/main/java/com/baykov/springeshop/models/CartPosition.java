package com.baykov.springeshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "product")
@Entity
@Table(name = "cart_positions", uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
public class CartPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Quantity should be specified.")
    @Min(value = 1, message = "Quantity should be valid.")
    private Long quantity;

    @NotNull(message = "Sum should be specified.")
    @Min(value = 0, message = "Sum should be positive.")
    @Transient
    private BigDecimal sum;

    public CartPosition(Product product, Cart cart) {
        this.product = product;
        this.quantity = 0L;
        this.sum = BigDecimal.ZERO;
        this.cart = cart;
    }
}