package com.smartinventorysystem.modules.customer.service;

import com.smartinventorysystem.constants.MessageConstants;
import com.smartinventorysystem.exceptions.BadRequestException;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.modules.customer.dto.request.CreateCustomerRequest;
import com.smartinventorysystem.modules.customer.dto.request.UpdateCustomerRequest;
import com.smartinventorysystem.modules.customer.dto.response.CustomerResponse;
import com.smartinventorysystem.modules.customer.entity.Customer;
import com.smartinventorysystem.modules.customer.mapper.CustomerMapper;
import com.smartinventorysystem.modules.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final Clock clock;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if (request.getEmail() != null && customerRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Customer already exists with email: " + request.getEmail());
        }

        Customer customer = customerMapper.toEntity(request);
        customer.setCreatedAt(LocalDateTime.now(clock));

        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse updateCustomer(Integer customerId, UpdateCustomerRequest request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CUSTOMER_NOT_FOUND));

        if (request.getCustomerName() != null && !request.getCustomerName().isBlank()) {
            customer.setCustomerName(request.getCustomerName());
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            customer.setPhone(request.getPhone());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            customer.setEmail(request.getEmail());
        }

        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }

        customer.setUpdatedAt(LocalDateTime.now(clock));

        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Integer customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CUSTOMER_NOT_FOUND));

        customerRepository.delete(customer);
    }

    @Override
    public CustomerResponse getCustomerById(Integer customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CUSTOMER_NOT_FOUND));

        return customerMapper.toResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerMapper.toResponseList(customerRepository.findAll());
    }
}