package com.adme.products.prices.rest.controllers;

import com.adme.products.prices.domain.models.ProductPrice;
import com.adme.products.prices.domain.ports.ProductsPricesPersistence;
import com.adme.products.prices.rest.TestApplication;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsPricesControllerIT {

    private static final RestAssuredConfig REST_ASSURED_CONFIG = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
    @LocalServerPort
    int port;
    @MockBean
    private ProductsPricesPersistence productsPricesPersistence;

    @ParameterizedTest
    @CsvSource({
            "1,35455,2020-06-14T10:00:00,1,35.50,EUR,2020-06-14T01:00:00,2020-06-15T01:00:00"
    })
    void given_QueryParams_When_CallingProductsPricesThatExists_Then_OkResponse(Integer brandId, Integer productId, LocalDateTime date, Integer priceList, BigDecimal price, String currency, LocalDateTime startDate, LocalDateTime endDate) {

        ProductPrice productPrice = ProductPrice.builder()
                .productId(productId)
                .brandId(brandId)
                .priceList(priceList)
                .price(price)
                .currency(Currency.getInstance(currency))
                .startDate(startDate)
                .endDate(endDate)
                .build();

        Mockito.when(productsPricesPersistence.findProductPrice(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.of(productPrice));

        given()
            .config(REST_ASSURED_CONFIG)
            .queryParam("brandId", brandId)
            .queryParam("productId", productId)
            .queryParam("date", date.toString())
        .when()
            .get("http://localhost:" + port + "/products-prices")
        .then()
            .statusCode(200).assertThat()
            .body("productId", equalTo(productPrice.getProductId()),
                    "brandId", equalTo(productPrice.getBrandId()),
                    "priceList", equalTo(productPrice.getPriceList()),
                    "price", comparesEqualTo(productPrice.getPrice()),
                    "currency", equalTo(productPrice.getCurrency().getCurrencyCode()),
                    "startDate", lessThanOrEqualTo(date.toString()), // Comparing lexicographically
                    "endDate", greaterThanOrEqualTo(date.toString())
            );
    }

    @ParameterizedTest
    @CsvSource({
            "2,35455,2020-06-14T10:00:00"
    })
    void given_QueryParams_When_CallingProductsPricesThatNotExists_Then_NoContentResponse(Integer brandId, Integer productId, LocalDateTime date) {

        Mockito.when(productsPricesPersistence.findProductPrice(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        given()
            .config(REST_ASSURED_CONFIG)
            .queryParam("brandId", brandId)
            .queryParam("productId", productId)
            .queryParam("date", date.toString())
        .when()
            .get("http://localhost:" + port + "/products-prices")
        .then()
            .statusCode(204);
    }

    @ParameterizedTest
    @CsvSource({
            "A,35455,2020-06-14T10:00:00"
    })
    void given_QueryParams_When_CallingProductsPricesWrongQueryParams_Then_BadRequestResponse(String brandId, Integer productId, LocalDateTime date) {

        Mockito.when(productsPricesPersistence.findProductPrice(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        given()
            .config(REST_ASSURED_CONFIG)
            .queryParam("brandId", brandId)
            .queryParam("productId", productId)
            .queryParam("date", date.toString())
        .when()
            .get("http://localhost:" + port + "/products-prices")
        .then()
            .statusCode(400).assertThat()
            .body("error", equalTo("Bad Request"));
    }

}
