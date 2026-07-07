package com.smartinventorysystem.modules.supplier.service;

import com.smartinventorysystem.modules.supplier.dto.Request.CreateSupplierRequest;
import com.smartinventorysystem.modules.supplier.dto.Request.UpdateSupplierRequest;
import com.smartinventorysystem.modules.supplier.dto.Response.SupplierResponse;
import java.util.List;

public interface SupplierService {
    SupplierResponse createSupplier(CreateSupplierRequest request);
    SupplierResponse updateSupplier(Integer supplierId, UpdateSupplierRequest request);
    void deleteSupplier(Integer supplierId);
    SupplierResponse getSupplierById(Integer supplierId);
    List<SupplierResponse> getAllSuppliers();
}