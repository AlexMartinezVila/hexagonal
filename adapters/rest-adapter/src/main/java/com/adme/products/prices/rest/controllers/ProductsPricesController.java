package com.adme.products.prices.rest.controllers;

import com.adme.products.prices.domain.ports.ProductsPricesPersistence;
import com.adme.products.prices.rest.mappers.PricesMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ProductsPricesApi;
import org.openapitools.model.ProductPriceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
class ProductsPricesController implements ProductsPricesApi {

    private final ProductsPricesPersistence productsPricesPersistence;

    private static final PricesMapper pricesMapper = PricesMapper.INSTANCE;

    @Override
    public ResponseEntity<ProductPriceResponse> productsPricesGet(Integer productId, Integer brandId, LocalDateTime date) {
        return productsPricesPersistence.findProductPrice(productId, brandId, date)
                .map(pricesMapper::toProductPriceResponse)
                .map(productPriceResponse -> ResponseEntity.ok().body(productPriceResponse))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}