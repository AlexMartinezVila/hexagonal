package com.adme.products.prices.rest.mappers;

import com.adme.products.prices.domain.models.ProductPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.ProductPriceResponse;

@Mapper
public interface PricesMapper {

    PricesMapper INSTANCE = Mappers.getMapper(PricesMapper.class);

    @Mapping(source = "currency.currencyCode", target = "currency")
    ProductPriceResponse toProductPriceResponse(ProductPrice productPrice);

}
