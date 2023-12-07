package com.adme.products.prices.h2.mappers;

import com.adme.products.prices.h2.entities.PricesEntity;
import com.adme.products.prices.domain.models.ProductPrice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricesMapperUnitTest {

    private final PricesMapper pricesMapper = PricesMapperImpl.INSTANCE;

    @Test
    void given_PricesMapper_When_toProductPrice_Then_MappingSucceeds() {
        LocalDateTime atDateTime = LocalDateTime.of(2020, 6, 14, 15, 0, 0);

        PricesEntity pricesEntity = PricesEntity.builder()
                .brandId(1)
                .productId(1)
                .priceList(1)
                .price(BigDecimal.ONE)
                .currency(Currency.getInstance("EUR"))
                .startDate(atDateTime)
                .endDate(atDateTime)
                .build();

        ProductPrice productPrice = pricesMapper.toProductPrice(pricesEntity);

        assertEquals(pricesEntity.getBrandId(), productPrice.getBrandId());
        assertEquals(pricesEntity.getProductId(), productPrice.getProductId());
        assertEquals(pricesEntity.getPriceList(), productPrice.getPriceList());
        assertEquals(pricesEntity.getPrice(), productPrice.getPrice());
        assertEquals(pricesEntity.getCurrency(), productPrice.getCurrency());
        assertEquals(pricesEntity.getStartDate(), productPrice.getStartDate());
        assertEquals(pricesEntity.getEndDate(), productPrice.getEndDate());
    }

}
