package com.adme.products.prices.rest.controllers;

import com.adme.products.prices.domain.models.ProductPrice;
import com.adme.products.prices.domain.ports.ProductsPricesPersistence;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsPricesController.class)
@WithMockUser
class ProductsPricesControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductsPricesPersistence productsPricesPersistence;

    @ParameterizedTest
    @CsvSource({
            "1,35455,2020-06-14T10:00:00,1,35.50,EUR,2020-06-14T01:00:00,2020-06-15T01:00:00"
    })
    void given_QueryParams_When_CallingProductsPricesThatExists_Then_OkResponse(Integer brandId, Integer productId, LocalDateTime date, Integer priceList, BigDecimal price, String currency, LocalDateTime startDate, LocalDateTime endDate) throws Exception {

        ProductPrice productPrice = ProductPrice.builder()
                .productId(productId)
                .brandId(brandId)
                .priceList(priceList)
                .price(price)
                .currency(Currency.getInstance(currency))
                .startDate(startDate)
                .endDate(endDate)
                .build();

        when(productsPricesPersistence.findProductPrice(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.of(productPrice));

        this.mockMvc.perform(get("/products-prices")
                        .queryParam("brandId", brandId.toString())
                        .queryParam("productId", productId.toString())
                        .queryParam("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"productId\":35455,\"brandId\":1,\"priceList\":1,\"price\":35.50,\"currency\":\"EUR\",\"startDate\":\"2020-06-14T01:00:00\",\"endDate\":\"2020-06-15T01:00:00\"}"));
    }

    @ParameterizedTest
    @CsvSource({
            "2,35455,2020-06-14T10:00:00"
    })
    void given_QueryParams_When_CallingProductsPricesThatNotExists_Then_NoContentResponse(Integer brandId, Integer productId, LocalDateTime date) throws Exception {

        Mockito.when(productsPricesPersistence.findProductPrice(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/products-prices")
                        .queryParam("brandId", brandId.toString())
                        .queryParam("productId", productId.toString())
                        .queryParam("date", date.toString()))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @CsvSource({
            "A,35455,2020-06-14T10:00:00"
    })
    void given_QueryParams_When_CallingProductsPricesWrongQueryParams_Then_BadRequestResponse(String brandId, Integer productId, LocalDateTime date) throws Exception {

        Mockito.when(productsPricesPersistence.findProductPrice(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/products-prices")
                        .queryParam("brandId", brandId.toString())
                        .queryParam("productId", productId.toString())
                        .queryParam("date", date.toString()))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource({
            "1,35455,2020-06-14T10:00:00"
    })
    void given_QueryParams_When_ServerThrowsException_Then_InternalServerErrorResponse(Integer brandId, Integer productId, LocalDateTime date) throws Exception {

        Mockito.when(productsPricesPersistence.findProductPrice(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new RuntimeException("Testing Exception"));

        this.mockMvc.perform(get("/products-prices")
                        .queryParam("brandId", brandId.toString())
                        .queryParam("productId", productId.toString())
                        .queryParam("date", date.toString()))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(Matchers.containsString("Testing Exception")));
    }

    @ParameterizedTest
    @CsvSource({
            "1,35455,2020-06-14T10:00:00"
    })
    @WithAnonymousUser
    void given_QueryParams_When_WrongCredentials_Then_UnauthorizedResponse(Integer brandId, Integer productId, LocalDateTime date) throws Exception {

        this.mockMvc.perform(get("/products-prices")
                        .queryParam("brandId", brandId.toString())
                        .queryParam("productId", productId.toString())
                        .queryParam("date", date.toString()))
                .andExpect(status().isUnauthorized());
    }

}
