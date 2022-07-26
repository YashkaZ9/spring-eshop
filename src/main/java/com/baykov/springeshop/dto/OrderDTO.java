package com.baykov.springeshop.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private int productsCount;
    private BigDecimal totalSum = BigDecimal.ZERO;
    private List<OrderProductsDTO> products = new ArrayList<>();

    public void aggregate() {
        this.productsCount = products.size();
        for (OrderProductsDTO productInfo : products) {
            this.totalSum = this.totalSum.add(productInfo.getSum());
        }
    }
}
