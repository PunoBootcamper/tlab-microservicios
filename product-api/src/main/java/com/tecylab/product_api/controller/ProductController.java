package com.tecylab.product_api.controller;

import com.tecylab.product_api.dto.ProductLookupResponse;
import com.tecylab.product_api.dto.ProductRequest;
import com.tecylab.product_api.dto.ProductResponse;
import com.tecylab.product_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Controller", description = "Controller for managing products")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products"),
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "Create a new product", description = "Create a new product with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created the product"),
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.save(productRequest));
    }

    @Operation(summary = "Get a product by ID", description = "Retrieve a product by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Update a product", description = "Update an existing product with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the product"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(id, productRequest));
    }

    @GetMapping("/list")
    @Operation(summary = "Find products by Id list", description = "Retrieve a list of products by their unique identifiers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products"),
        @ApiResponse(responseCode = "404", description = "One or more products not found")
    })
    public ResponseEntity<List<ProductLookupResponse>> findProductsByIdList(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(productService.findProductsByIdList(ids));
    }


}
