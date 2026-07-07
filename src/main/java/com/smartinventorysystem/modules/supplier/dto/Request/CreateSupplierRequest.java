package com.smartinventorysystem.modules.supplier.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSupplierRequest {
    @NotBlank(message = "Supplier name is required")
    private String supplierName;
    @NotBlank(message = "Phone is required")
    private String phone;
    @Email(message = "Invalid email format")
    private String email;
    private String address;
}
