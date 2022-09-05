package com.baykov.springeshop.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderPosition> orderPositions;

    @NotNull(message = "Total sum should be specified.")
    @Min(value = 0, message = "Total sum should be positive.")
    @Column(name = "total_sum")
    private BigDecimal totalSum;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "planned_execution_date")
    private LocalDateTime plannedExecutionDate;

    @Column(name = "address")
    @Pattern(regexp = "[A-ZА-Я][A-Za-zА-Яа-я-.'\\s]*, [A-ZА-Я][A-Za-zА-Яа-я-.'\\s]*, \\d{6}",
            message = "Address should match the pattern: Country, City, Index (6 numbers)")
    private String address;

    @Column(name = "comment")
    @Size(max = 255, message = "Comment length should be not more than 255 symbols.")
    private String comment;

    public Order(User user) {
        this.user = user;
        this.orderPositions = new HashSet<>();
        this.totalSum = BigDecimal.ZERO;
        this.orderStatus = OrderStatus.NEW;
    }
}
