package com.smartinventorysystem.modules.productsupplier.service;

import com.smartinventorysystem.modules.productsupplier.dto.response.ProductSummaryResponse;
import com.smartinventorysystem.modules.productsupplier.dto.response.ProductSupplierResponse;
import com.smartinventorysystem.modules.productsupplier.dto.response.SupplierSummaryResponse;

import java.util.List;

public interface ProductSupplierService {

    void addSupplier(Integer productId, Integer supplierId);

    void removeSupplier(Integer productId, Integer supplierId);

    List<SupplierSummaryResponse> getSuppliersByProduct(Integer productId);

    List<ProductSummaryResponse> getProductsBySupplier(Integer supplierId);
}
