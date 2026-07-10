package com.smartinventorysystem.modules.customer.mapper;

import com.smartinventorysystem.modules.customer.dto.request.CreateCustomerRequest;
import com.smartinventorysystem.modules.customer.dto.response.CustomerResponse;
import com.smartinventorysystem.modules.customer.entity.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CreateCustomerRequest request);
    CustomerResponse toResponse(Customer customer);

    List<CustomerResponse> toResponseList(List<Customer> customers);
}
