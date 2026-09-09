package com.smartinventorysystem.modules.productsupplier.dto.response;

import lombok.Data;

@Data
public class ProductSupplierResponse {
    private Integer productId;
    private String productName;

    private Integer supplierId;
    private String supplierName;
}
