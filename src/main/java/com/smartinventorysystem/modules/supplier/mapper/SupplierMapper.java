package com.smartinventorysystem.modules.supplier.mapper;

import com.smartinventorysystem.modules.supplier.dto.Request.CreateSupplierRequest;
import com.smartinventorysystem.modules.supplier.dto.Response.SupplierResponse;
import com.smartinventorysystem.modules.supplier.entity.Supplier;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(CreateSupplierRequest request);

    SupplierResponse toResponse(Supplier supplier);

    List<SupplierResponse> toResponseList(List<Supplier> suppliers);
}