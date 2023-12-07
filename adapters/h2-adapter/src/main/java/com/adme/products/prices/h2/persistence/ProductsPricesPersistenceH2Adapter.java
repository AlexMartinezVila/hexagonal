package com.adme.products.prices.h2.persistence;

import com.adme.products.prices.h2.entities.QPricesEntity;
import com.adme.products.prices.h2.mappers.PricesMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import com.adme.products.prices.domain.models.ProductPrice;
import org.springframework.stereotype.Repository;
import com.adme.products.prices.domain.ports.ProductsPricesPersistence;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProductsPricesPersistenceH2Adapter implements ProductsPricesPersistence {

    private final JPAQueryFactory queryFactory;

    private static final PricesMapper pricesMapper = PricesMapper.INSTANCE;

    @Override
    public Optional<ProductPrice> findProductPrice(Integer productId, Integer brandId, LocalDateTime atDateTime) {
        QPricesEntity qPrices = QPricesEntity.pricesEntity;
        return queryFactory.select(qPrices)
                .from(qPrices)
                .where(
                        qPrices.brandId.eq(brandId),
                        qPrices.productId.eq(productId),
                        qPrices.startDate.loe(atDateTime),
                        qPrices.endDate.goe(atDateTime)
                )
                .groupBy(qPrices.id, qPrices.brandId, qPrices.productId, qPrices.currency)
                .orderBy(qPrices.priority.desc())
                .limit(1)
                .stream()
                .findFirst()
                .map(pricesMapper::toProductPrice);
    }
}