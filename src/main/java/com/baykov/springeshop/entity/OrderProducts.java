package com.baykov.springeshop.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders_products")
public class OrderProducts {
    @EmbeddedId
    private OrderProductsId orderProductsId;

    private BigDecimal quantity;

    private BigDecimal price;

    public OrderProducts(Order order, Product product, BigDecimal quantity) {
        this.orderProductsId.setOrder(order);
        this.orderProductsId.setProduct(product);
        this.quantity = quantity;
        this.price = product.getPrice();
    }
}
