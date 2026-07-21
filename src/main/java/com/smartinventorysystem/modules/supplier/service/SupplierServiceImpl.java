package com.smartinventorysystem.modules.supplier.service;

import com.smartinventorysystem.exceptions.DuplicateSupplierException;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.modules.supplier.dto.request.CreateSupplierRequest;
import com.smartinventorysystem.modules.supplier.dto.request.UpdateSupplierRequest;
import com.smartinventorysystem.modules.supplier.dto.response.SupplierResponse;
import com.smartinventorysystem.modules.supplier.entity.Supplier;
import com.smartinventorysystem.modules.supplier.mapper.SupplierMapper;
import com.smartinventorysystem.modules.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final Clock clock;

    @Override
    public SupplierResponse createSupplier(CreateSupplierRequest request) {

        if (supplierRepository.existsBySupplierName(request.getSupplierName())) {
            throw new DuplicateSupplierException("Supplier already exists with name: " + request.getSupplierName());
        }

        if (request.getEmail() != null && supplierRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateSupplierException("Supplier already exists with email: " + request.getEmail());
        }

        if (request.getPhone() != null && supplierRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateSupplierException("Supplier already exists with Phone: " + request.getPhone());
        }

        Supplier supplier = supplierMapper.toEntity(request);
        supplier.setCreatedAt(LocalDateTime.now(clock));

        return supplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponse updateSupplier(Integer supplierId, UpdateSupplierRequest request) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));


        if (request.getSupplierName() != null
                && !request.getSupplierName().isBlank()) {

            if (supplierRepository.existsBySupplierNameAndSupplierIdNot(
                    request.getSupplierName(), supplierId)) {

                throw new DuplicateSupplierException(
                        "Supplier already exists with name: " + request.getSupplierName()
                );
            }

            supplier.setSupplierName(request.getSupplierName());
        }


        if (request.getEmail() != null
                && !request.getEmail().isBlank()) {

            if (supplierRepository.existsByEmailAndSupplierIdNot(
                    request.getEmail(), supplierId)) {

                throw new DuplicateSupplierException(
                        "Supplier already exists with email: " + request.getEmail()
                );
            }

            supplier.setEmail(request.getEmail());
        }


        if (request.getPhone() != null
                && !request.getPhone().isBlank()) {

            if (supplierRepository.existsByPhoneAndSupplierIdNot(
                    request.getPhone(), supplierId)) {

                throw new DuplicateSupplierException(
                        "Supplier already exists with phone: " + request.getPhone()
                );
            }

            supplier.setPhone(request.getPhone());
        }


        if (request.getAddress() != null) {
            supplier.setAddress(request.getAddress());
        }

        supplier.setUpdatedAt(LocalDateTime.now(clock));

        return supplierMapper.toResponse(
                supplierRepository.save(supplier)
        );
    }

    @Override
    public void deleteSupplier(Integer supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        supplierRepository.delete(supplier);
    }

    @Override
    public SupplierResponse getSupplierById(Integer supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        return supplierMapper.toResponse(supplier);
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return supplierMapper.toResponseList(supplierRepository.findAll());
    }
}