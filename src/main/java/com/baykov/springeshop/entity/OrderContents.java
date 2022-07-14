package com.baykov.springeshop.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_contents")
public class OrderContents {
    private static final String SEQ_NAME = "order_contents_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal quantity;

    private BigDecimal price;

    public OrderContents(Order order, Product product, Long quantity) {
        this.order = order;
        this.product = product;
        this.quantity = new BigDecimal(quantity);
        this.price = product.getPrice();
    }
}
