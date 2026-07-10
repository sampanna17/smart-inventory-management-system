package com.smartinventorysystem.modules.supplier.mapper;

import com.smartinventorysystem.modules.supplier.dto.request.CreateSupplierRequest;
import com.smartinventorysystem.modules.supplier.dto.response.SupplierResponse;
import com.smartinventorysystem.modules.supplier.entity.Supplier;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(CreateSupplierRequest request);

    SupplierResponse toResponse(Supplier supplier);

    List<SupplierResponse> toResponseList(List<Supplier> suppliers);
}