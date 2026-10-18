package com.tecylab.product_api.mapper;

import com.tecylab.product_api.dto.ProductRequest;
import com.tecylab.product_api.dto.ProductResponse;
import com.tecylab.product_api.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequest productRequest);

    ProductResponse toResponse(Product product);

    void updateEntity(ProductRequest productRequest, @MappingTarget Product product);
}
