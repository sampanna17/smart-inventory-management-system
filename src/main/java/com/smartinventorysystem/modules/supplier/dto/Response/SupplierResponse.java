package com.smartinventorysystem.modules.supplier.dto.Response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SupplierResponse {
    private Integer supplierID;
    private String supplierName;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
