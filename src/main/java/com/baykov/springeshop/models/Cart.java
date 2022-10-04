package com.baykov.springeshop.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "cartPositions")
@ToString(exclude = "cartPositions")
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<CartPosition> cartPositions = new ArrayList<>();

    @NotNull(message = "Total sum should be specified.")
    @Min(value = 0, message = "Total sum should be positive.")
    @Transient
    private BigDecimal totalSum;

    public Cart(User user) {
        this.user = user;
        this.totalSum = BigDecimal.ZERO;
    }
}
