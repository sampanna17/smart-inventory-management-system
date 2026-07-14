package com.smartinventorysystem.modules.productsupplier.mapper;

import com.smartinventorysystem.modules.productsupplier.dto.response.ProductSummaryResponse;
import com.smartinventorysystem.modules.productsupplier.dto.response.ProductSupplierResponse;
import com.smartinventorysystem.modules.productsupplier.dto.response.SupplierSummaryResponse;
import com.smartinventorysystem.modules.productsupplier.entity.ProductSupplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductSupplierMapper {

    @Mapping(source = "supplier.supplierId", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierName")
    SupplierSummaryResponse toSupplierResponse(ProductSupplier productSupplier);

    List<SupplierSummaryResponse> toSupplierResponseList(List<ProductSupplier> productSuppliers);

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    ProductSummaryResponse toProductResponse(ProductSupplier productSupplier);

    List<ProductSummaryResponse> toProductResponseList(List<ProductSupplier> productSuppliers);

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "supplier.supplierId", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierName")
    ProductSupplierResponse toResponse(ProductSupplier productSupplier);

    List<ProductSupplierResponse> toResponseList(
            List<ProductSupplier> productSuppliers
    );
}