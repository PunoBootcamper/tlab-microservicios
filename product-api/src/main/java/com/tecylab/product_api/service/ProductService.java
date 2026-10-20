package com.tecylab.product_api.service;

import com.tecylab.product_api.dto.ProductLookupResponse;
import com.tecylab.product_api.dto.ProductRequest;
import com.tecylab.product_api.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    ProductResponse save(ProductRequest productRequest);

    ProductResponse update(Long id, ProductRequest updatedProduct);

    List<ProductLookupResponse> findProductsByIdList(List<Long> ids);
}
