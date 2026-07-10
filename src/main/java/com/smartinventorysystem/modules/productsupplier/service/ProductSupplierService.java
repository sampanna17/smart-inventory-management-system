package com.smartinventorysystem.modules.productsupplier.service;

import com.smartinventorysystem.modules.productsupplier.dto.response.ProductSupplierResponse;

import java.util.List;

public interface ProductSupplierService {

    void addSupplier(Integer productId, Integer supplierId);

    void removeSupplier(Integer productId, Integer supplierId);

    List<ProductSupplierResponse> getSuppliersByProduct(Integer productId);

    List<ProductSupplierResponse> getProductsBySupplier(Integer supplierId);
}
