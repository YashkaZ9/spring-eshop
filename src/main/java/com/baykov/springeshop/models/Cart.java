package com.baykov.springeshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartPosition> cartPositions;

    @NotNull(message = "Total sum should be specified.")
    @Min(value = 0, message = "Total sum should be positive.")
    @Column(name = "total_sum")
    private BigDecimal totalSum;

    public Cart(User user) {
        this.user = user;
        this.cartPositions = new HashSet<>();
        this.totalSum = BigDecimal.ZERO;
    }
}
