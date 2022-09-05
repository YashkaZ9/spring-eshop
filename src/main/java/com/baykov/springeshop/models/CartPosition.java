package com.baykov.springeshop.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "product")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_positions", uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
public class CartPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @NotNull(message = "Quantity should be specified.")
    @Min(value = 1, message = "Quantity should be valid.")
    private Long quantity;

    @NotNull(message = "Sum should be specified.")
    @Min(value = 0, message = "Sum should be positive.")
    @Column(name = "sum")
    private BigDecimal sum;

    public CartPosition(Product product, Cart cart) {
        this.product = product;
        this.quantity = 0L;
        this.sum = BigDecimal.ZERO;
        this.cart = cart;
    }

    public CartPosition(Product product) {
        this(product, null);
    }
}