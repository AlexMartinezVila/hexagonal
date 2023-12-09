package com.adme.products.prices;

import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsPricesE2ETest {

    @LocalServerPort
    int port;

    @ParameterizedTest
    @CsvSource({
            "1,35455,2020-06-14T10:00:00,1,35.50,EUR",
            "1,35455,2020-06-14T16:00:00,2,25.45,EUR",
            "1,35455,2020-06-14T21:00:00,1,35.50,EUR",
            "1,35455,2020-06-15T10:00:00,3,30.50,EUR",
            "1,35455,2020-06-16T21:00:00,4,38.95,EUR"
    })
    void given_QueryParams_When_CallingProductsPrices_Then_ExpectedResponse(Integer brandId, Integer productId, LocalDateTime date, Integer priceList, BigDecimal price, String currency) {
        given()
            .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL)))
            .auth().basic("user", "pass")
            .queryParam("brandId", brandId)
            .queryParam("productId", productId)
            .queryParam("date", date.toString())
        .when()
            .get("http://localhost:" + port + "/products-prices")
        .then()
            .statusCode(200).assertThat()
            .body("productId", equalTo(productId),
                    "brandId", equalTo(brandId),
                    "priceList", equalTo(priceList),
                    "price", comparesEqualTo(price),
                    "currency", equalTo(currency),
                    "startDate", lessThanOrEqualTo(date.toString()), // Comparing lexicographically
                    "endDate", greaterThanOrEqualTo(date.toString())
            );
    }

}
