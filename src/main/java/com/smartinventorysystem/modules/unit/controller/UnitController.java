package com.smartinventorysystem.modules.unit.controller;

import com.smartinventorysystem.constants.ApiRoutes;
import com.smartinventorysystem.common.dto.ApiResponse;
import com.smartinventorysystem.modules.unit.dto.Request.CreateUnitRequest;
import com.smartinventorysystem.modules.unit.dto.Request.UpdateUnitRequest;
import com.smartinventorysystem.modules.unit.dto.Response.UnitResponse;
import com.smartinventorysystem.modules.unit.service.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(ApiRoutes.Units.BASE)
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping(ApiRoutes.Units.CREATE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UnitResponse>> createUnit(
            @Valid @RequestBody CreateUnitRequest request) {

        UnitResponse response = unitService.createUnit(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<UnitResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .success(true)
                        .message("Unit created successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping(ApiRoutes.Units.UPDATE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UnitResponse>> updateUnit(
            @PathVariable Integer unitId,
            @RequestBody UpdateUnitRequest request) {

        UnitResponse response = unitService.updateUnit(unitId, request);

        return ResponseEntity.ok(
                ApiResponse.<UnitResponse>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Unit updated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping(ApiRoutes.Units.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUnit(@PathVariable Integer unitId) {

        unitService.deleteUnit(unitId);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Unit deleted successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping(ApiRoutes.Units.GET_BY_ID)
    public ResponseEntity<ApiResponse<UnitResponse>> getById(@PathVariable Integer unitId) {

        UnitResponse response = unitService.getUnitById(unitId);

        return ResponseEntity.ok(
                ApiResponse.<UnitResponse>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Unit fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping(ApiRoutes.Units.GET_ALL)
    public ResponseEntity<ApiResponse<List<UnitResponse>>> getAll() {

        List<UnitResponse> response = unitService.getAllUnits();

        return ResponseEntity.ok(
                ApiResponse.<List<UnitResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Units fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}