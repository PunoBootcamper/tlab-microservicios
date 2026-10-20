package com.tecylab.product_api.service.impl;

import com.tecylab.product_api.OperationLog;
import com.tecylab.product_api.dto.ProductLookupResponse;
import com.tecylab.product_api.dto.ProductRequest;
import com.tecylab.product_api.dto.ProductResponse;
import com.tecylab.product_api.exception.ProductNotFoundException;
import com.tecylab.product_api.mapper.ProductMapper;
import com.tecylab.product_api.model.Product;
import com.tecylab.product_api.repository.ProductRepository;
import com.tecylab.product_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @OperationLog(type = OperationLog.OperationType.READ, description = "Todos los productos encontrados")
    @Cacheable(value = "products", key = "'allProducts'")
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(productMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @OperationLog(type = OperationLog.OperationType.READ, description = "Producto encontrado: #id")
    @Cacheable(value = "products", key = "#id")
    public ProductResponse findById(Long id) {
        return productRepository.findById(id).
        map(productMapper::toResponse)
        .orElseThrow(
                () -> new ProductNotFoundException("Producto no encontrado con id: " + id)
        );
    }

    @Override
    @OperationLog(type = OperationLog.OperationType.CREATE, description = "Producto creado: #id")
    @CacheEvict(value = "products", key = "'allProducts'")
    public ProductResponse save(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @OperationLog(type = OperationLog.OperationType.UPDATE, description = "Producto actualizado: #id")
    @CacheEvict(value = "products", key = "#id")
    public ProductResponse update(Long id, ProductRequest updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id: " + id));

        productMapper.updateEntity(updatedProduct, existingProduct);
        Product savedProduct = productRepository.save(existingProduct);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public List<ProductLookupResponse> findProductsByIdList(List<Long> ids) {
        Map<Long, Product> productsById = productRepository.findByIdIn(ids)
                .stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        product -> product
                ));

        return ids.stream()
                .map(id -> {
                    Product product = productsById.get(id);

                    return product != null
                            ? productMapper.toLookupResponse(product)
                            : new ProductLookupResponse(
                            id,
                            null,
                            null,
                            null,
                            null,
                            false
                    );
                })
                .toList();

    }
}
