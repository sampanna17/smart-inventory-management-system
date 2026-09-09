package com.smartinventorysystem.modules.customer.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomerResponse {
    private Integer customerID;
    private String customerName;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
