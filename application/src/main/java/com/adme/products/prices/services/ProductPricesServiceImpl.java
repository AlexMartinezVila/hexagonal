package com.adme.products.prices.services;

import com.adme.products.prices.domain.models.ProductPrice;
import com.adme.products.prices.domain.ports.ProductsPricesPersistence;
import com.adme.products.prices.domain.ports.ProductsPricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductPricesServiceImpl implements ProductsPricesService {

    private final ProductsPricesPersistence productsPricesPersistence;

    public Optional<ProductPrice> findProductPrice(Integer productId, Integer brandId, LocalDateTime atDateTime) {
        return productsPricesPersistence.findProductPrice(productId, brandId, atDateTime);
    }

}
