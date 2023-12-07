package com.adme.products.prices.h2.mappers;

import com.adme.products.prices.h2.entities.PricesEntity;
import com.adme.products.prices.domain.models.ProductPrice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PricesMapper {

    PricesMapper INSTANCE = Mappers.getMapper(PricesMapper.class);

    ProductPrice toProductPrice(PricesEntity pricesEntity);

}
