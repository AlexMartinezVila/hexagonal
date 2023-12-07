package com.adme.products.prices.h2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRICES")
public class PricesEntity {

    @Id
    private Integer id;

    @NotNull
    private Integer brandId;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private Integer productId;

    @NotNull
    private Integer priceList;

    @NotNull
    private Integer priority;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Column(name = "CURR")
    private Currency currency;


}