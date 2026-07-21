package com.smartinventorysystem.modules.unit.service;

import com.smartinventorysystem.constants.MessageConstants;
import com.smartinventorysystem.exceptions.DuplicateUnitException;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.modules.unit.dto.request.CreateUnitRequest;
import com.smartinventorysystem.modules.unit.dto.request.UpdateUnitRequest;
import com.smartinventorysystem.modules.unit.dto.response.UnitResponse;
import com.smartinventorysystem.modules.unit.entity.Unit;
import com.smartinventorysystem.modules.unit.mapper.UnitMapper;
import com.smartinventorysystem.modules.unit.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @Override
    public UnitResponse createUnit(CreateUnitRequest request) {

        if (unitRepository.existsByUnitName(request.getUnitName())) {
            throw new DuplicateUnitException("Unit already exists with name: " + request.getUnitName());
        }

        Unit unit = unitMapper.toEntity(request);

        return unitMapper.toResponse(unitRepository.save(unit));
    }

    @Override
    public UnitResponse updateUnit(Integer unitId, UpdateUnitRequest request) {

        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.UNIT_NOT_FOUND));

        if (request.getUnitName() != null && !request.getUnitName().isBlank()) {
            unit.setUnitName(request.getUnitName());
        }

        return unitMapper.toResponse(unitRepository.save(unit));
    }

    @Override
    public void deleteUnit(Integer unitId) {

        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.UNIT_NOT_FOUND));

        unitRepository.delete(unit);
    }

    @Override
    public UnitResponse getUnitById(Integer unitId) {

        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.UNIT_NOT_FOUND));

        return unitMapper.toResponse(unit);
    }

    @Override
    public List<UnitResponse> getAllUnits() {
        return unitMapper.toResponseList(unitRepository.findAll());
    }
}