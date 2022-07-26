package com.baykov.springeshop.dto;

import com.baykov.springeshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductsDTO {
    private Long article;
    private String title;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal sum;

    public OrderProductsDTO(Product product) {
        this.article = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.quantity = BigDecimal.ONE;
        this.sum = product.getPrice();
    }
}
