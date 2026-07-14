package com.smartinventorysystem.modules.productsupplier.controller;

import com.smartinventorysystem.common.dto.ApiResponse;
import com.smartinventorysystem.constants.ApiRoutes;
import com.smartinventorysystem.modules.productsupplier.dto.response.SupplierSummaryResponse;
import com.smartinventorysystem.modules.productsupplier.dto.response.ProductSummaryResponse;
import com.smartinventorysystem.modules.productsupplier.service.ProductSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(ApiRoutes.ProductSuppliers.BASE)
@RequiredArgsConstructor
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;

    @PostMapping(ApiRoutes.ProductSuppliers.ADD)
    public ResponseEntity<ApiResponse<Void>> addSupplier(
            @PathVariable Integer productId,
            @PathVariable Integer supplierId
    ) {

        productSupplierService.addSupplier(productId, supplierId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<Void>builder()
                                .status(HttpStatus.CREATED.value())
                                .success(true)
                                .message("Supplier assigned to product successfully")
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @DeleteMapping(ApiRoutes.ProductSuppliers.REMOVE)
    public ResponseEntity<ApiResponse<Void>> removeSupplier(
            @PathVariable Integer productId,
            @PathVariable Integer supplierId
    ) {

        productSupplierService.removeSupplier(productId, supplierId);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Supplier removed from product successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping(ApiRoutes.ProductSuppliers.GET_SUPPLIERS_BY_PRODUCT)
    public ResponseEntity<ApiResponse<List<SupplierSummaryResponse>>> getSuppliersByProduct(
            @PathVariable Integer productId
    ) {

        List<SupplierSummaryResponse> response =
                productSupplierService.getSuppliersByProduct(productId);

        return ResponseEntity.ok(
                ApiResponse.<List<SupplierSummaryResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Suppliers retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping(ApiRoutes.ProductSuppliers.GET_PRODUCTS_BY_SUPPLIER)
    public ResponseEntity<ApiResponse<List<ProductSummaryResponse>>> getProductsBySupplier(
            @PathVariable Integer supplierId
    ) {

        List<ProductSummaryResponse> response =
                productSupplierService.getProductsBySupplier(supplierId);

        return ResponseEntity.ok(
                ApiResponse.<List<ProductSummaryResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Products retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}