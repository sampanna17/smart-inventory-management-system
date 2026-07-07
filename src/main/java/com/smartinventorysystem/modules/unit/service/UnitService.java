package com.smartinventorysystem.modules.unit.service;

import com.smartinventorysystem.modules.unit.dto.Request.CreateUnitRequest;
import com.smartinventorysystem.modules.unit.dto.Request.UpdateUnitRequest;
import com.smartinventorysystem.modules.unit.dto.Response.UnitResponse;
import java.util.List;

public interface UnitService {
    UnitResponse createUnit(CreateUnitRequest request);
    UnitResponse updateUnit(Integer unitId, UpdateUnitRequest request);
    void deleteUnit(Integer unitId);
    UnitResponse getUnitById(Integer unitId);
    List<UnitResponse> getAllUnits();
}
