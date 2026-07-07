package com.smartinventorysystem.modules.customer.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Size(min = 10, max = 10, message = "Phone must be exactly 10 digits")
    @NotBlank(message = "Phone is required")
    private String phone;
    @Email(message = "Invalid email format")
    private String email;
    private String address;
}
