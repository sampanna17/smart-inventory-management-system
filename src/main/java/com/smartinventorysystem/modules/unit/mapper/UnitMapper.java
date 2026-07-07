package com.smartinventorysystem.modules.unit.mapper;

import com.smartinventorysystem.modules.unit.dto.Request.CreateUnitRequest;
import com.smartinventorysystem.modules.unit.dto.Response.UnitResponse;
import com.smartinventorysystem.modules.unit.entity.Unit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    Unit toEntity(CreateUnitRequest request);
    UnitResponse toResponse(Unit unit);

    List<UnitResponse> toResponseList(List<Unit> units);
}