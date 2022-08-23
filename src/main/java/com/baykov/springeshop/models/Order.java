package com.baykov.springeshop.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull
    @Column(name = "planned_execution_date")
    private LocalDateTime plannedExecutionDate;

    @Column(name = "address")
    @Pattern(regexp = "[A-Za-z][A-Za-zА-Яа-я-.'\\s]*, [A-Za-z][A-Za-zА-Яа-я-.'\\s]*, \\d{6}",
            message = "Address should match the pattern: Country, City, Index (6 numbers)")
    private String address;

    @Column(name = "comment")
    @Size(max = 255, message = "Comment length should be not more than 255 symbols.")
    private String comment;
}
