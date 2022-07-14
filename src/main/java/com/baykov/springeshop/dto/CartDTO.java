package com.baykov.springeshop.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private int productsCount;
    private BigDecimal totalSum = BigDecimal.ZERO;
    private List<CartDetailsDTO> products = new ArrayList<>();

    public void aggregate() {
        this.productsCount = products.size();
        for (CartDetailsDTO productInfo : products) {
            this.totalSum = this.totalSum.add(productInfo.getSum());
        }
    }
}
