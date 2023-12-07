package com.adme.products.prices.domain.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Builder
public class ProductPrice {

    private Integer brandId;
    private Integer productId;
    private Integer priceList;
    private BigDecimal price;
    private Currency currency;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
