package com.adme.products.prices.domain.ports;

import com.adme.products.prices.domain.models.ProductPrice;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProductsPricesService {

    Optional<ProductPrice> findProductPrice(Integer productId, Integer brandId, LocalDateTime localDateTime);

}
