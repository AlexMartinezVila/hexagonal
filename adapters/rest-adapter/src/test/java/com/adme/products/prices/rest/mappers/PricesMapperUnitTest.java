package com.adme.products.prices.rest.mappers;

import com.adme.products.prices.domain.models.ProductPrice;
import org.junit.jupiter.api.Test;
import org.openapitools.model.ProductPriceResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricesMapperUnitTest {

    private final PricesMapper pricesMapper = PricesMapperImpl.INSTANCE;

    @Test
    void given_PricesMapper_When_toProductPriceResponse_Then_MappingSucceeds() {
        LocalDateTime atDateTime = LocalDateTime.of(2020, 6, 14, 15, 0, 0);

        ProductPrice productPrice = ProductPrice.builder()
                .brandId(1)
                .productId(1)
                .priceList(1)
                .price(BigDecimal.ONE)
                .currency(Currency.getInstance("EUR"))
                .startDate(atDateTime)
                .endDate(atDateTime)
                .build();

        ProductPriceResponse productPriceResponse = pricesMapper.toProductPriceResponse(productPrice);

        assertEquals(productPrice.getBrandId(), productPriceResponse.getBrandId());
        assertEquals(productPrice.getProductId(), productPriceResponse.getProductId());
        assertEquals(productPrice.getPriceList(), productPriceResponse.getPriceList());
        assertEquals(productPrice.getPrice(), productPriceResponse.getPrice());
        assertEquals(productPrice.getCurrency().getCurrencyCode(), productPriceResponse.getCurrency());
        assertEquals(productPrice.getStartDate(), productPriceResponse.getStartDate());
        assertEquals(productPrice.getEndDate(), productPriceResponse.getEndDate());
    }

}
