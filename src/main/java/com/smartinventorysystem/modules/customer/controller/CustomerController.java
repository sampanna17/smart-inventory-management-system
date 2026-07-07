package com.smartinventorysystem.modules.customer.controller;

import com.smartinventorysystem.constants.ApiRoutes;
import com.smartinventorysystem.common.dto.ApiResponse;
import com.smartinventorysystem.modules.customer.dto.Request.CreateCustomerRequest;
import com.smartinventorysystem.modules.customer.dto.Request.UpdateCustomerRequest;
import com.smartinventorysystem.modules.customer.dto.Response.CustomerResponse;
import com.smartinventorysystem.modules.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(ApiRoutes.Customers.BASE)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(ApiRoutes.Customers.CREATE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {

        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CustomerResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .success(true)
                        .message("Customer created successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping(ApiRoutes.Customers.UPDATE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Integer customerId,
            @RequestBody UpdateCustomerRequest request) {

        CustomerResponse response = customerService.updateCustomer(customerId, request);

        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Customer updated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping(ApiRoutes.Customers.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Integer customerId) {

        customerService.deleteCustomer(customerId);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Customer deleted successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping(ApiRoutes.Customers.GET_BY_ID)
    public ResponseEntity<ApiResponse<CustomerResponse>> getById(@PathVariable Integer customerId) {

        CustomerResponse response = customerService.getCustomerById(customerId);

        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Customer fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping(ApiRoutes.Customers.GET_ALL)
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAll() {

        List<CustomerResponse> response = customerService.getAllCustomers();

        return ResponseEntity.ok(
                ApiResponse.<List<CustomerResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Customers fetched successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}