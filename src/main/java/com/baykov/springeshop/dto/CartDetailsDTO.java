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
public class CartDetailsDTO {
    private Long productId;
    private String title;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal sum;

    public CartDetailsDTO(Product product) {
        this.productId = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.quantity = BigDecimal.ONE;
        this.sum = product.getPrice();
    }
}
