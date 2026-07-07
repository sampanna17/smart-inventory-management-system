package com.smartinventorysystem.modules.customer.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private String customerName;

    @Size(min = 10, max = 10, message = "Phone must be exactly 10 digits")

    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    private String address;
}