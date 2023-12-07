package com.adme.products.prices.h2.persistence;

import com.adme.products.prices.domain.models.ProductPrice;
import com.adme.products.prices.h2.TestApplication;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestApplication.class)
class ProductsPricesPersistenceH2AdapterIT {

    @Autowired
    private ProductsPricesPersistenceH2Adapter productsPricesPersistenceH2Adapter;

    @ParameterizedTest
    @CsvSource({
            "1,35455,2020-06-14T10:00:00,1,35.50,EUR",
            "1,35455,2020-06-14T16:00:00,2,25.45,EUR",
            "1,35455,2020-06-14T21:00:00,1,35.50,EUR",
            "1,35455,2020-06-15T10:00:00,3,30.50,EUR",
            "1,35455,2020-06-16T21:00:00,4,38.95,EUR"
    })
    void given_QueryParams_When_CallingProductsPricesThatExists_Then_ExpectedResponse(Integer brandId, Integer productId, LocalDateTime date, Integer priceList, BigDecimal price, String currency) {
        Optional<ProductPrice> productPrice = productsPricesPersistenceH2Adapter.findProductPrice(productId, brandId, date);

        assertTrue(productPrice.isPresent());
        assertEquals(productId, productPrice.get().getProductId());
        assertEquals(brandId, productPrice.get().getBrandId());
        assertEquals(priceList, productPrice.get().getPriceList());
        assertEquals(price, productPrice.get().getPrice());
        assertEquals(currency, productPrice.get().getCurrency().getCurrencyCode());
        assertTrue(productPrice.get().getStartDate().isBefore(date) || productPrice.get().getStartDate().isEqual(date));
        assertTrue(productPrice.get().getEndDate().isAfter(date) || productPrice.get().getEndDate().isEqual(date));
    }

    @ParameterizedTest
    @CsvSource({
            "2,35455,2020-06-14T10:00:00",
            "1,235455,2020-06-14T16:00:00",
            "1,35455,2022-06-14T21:00:00"
    })
    void given_QueryParams_When_CallingProductsPricesThatNotExists_Then_NotPresent(Integer brandId, Integer productId, LocalDateTime date) {
        Optional<ProductPrice> productPrice = productsPricesPersistenceH2Adapter.findProductPrice(productId, brandId, date);

        assertFalse(productPrice.isPresent());
    }

}
