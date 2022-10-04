package com.baykov.springeshop.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "orderPositions")
@ToString(exclude = "orderPositions")
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderPosition> orderPositions = new ArrayList<>();

    @NotNull(message = "Total sum should be specified.")
    @Min(value = 0, message = "Total sum should be positive.")
    private BigDecimal totalSum;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime plannedExecutionDate;

    @Pattern(regexp = "[A-ZА-Я][A-Za-zА-Яа-я-.'\\s]*, [A-ZА-Я][A-Za-zА-Яа-я-.'\\s]*, \\d{6}",
            message = "Address should match the pattern: Country, City, Index (6 numbers)")
    private String address;

    @Size(max = 255, message = "Comment length should be not more than 255 symbols.")
    private String comment;

    public Order(User user) {
        this.user = user;
        this.totalSum = BigDecimal.ZERO;
        this.orderStatus = OrderStatus.NEW;
    }
}
