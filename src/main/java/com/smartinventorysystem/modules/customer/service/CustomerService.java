package com.smartinventorysystem.modules.customer.service;

import com.smartinventorysystem.modules.customer.dto.Request.CreateCustomerRequest;
import com.smartinventorysystem.modules.customer.dto.Request.UpdateCustomerRequest;
import com.smartinventorysystem.modules.customer.dto.Response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CreateCustomerRequest request);

    CustomerResponse updateCustomer(Integer customerId, UpdateCustomerRequest request);

    void deleteCustomer(Integer customerId);

    CustomerResponse getCustomerById(Integer customerId);

    List<CustomerResponse> getAllCustomers();
}