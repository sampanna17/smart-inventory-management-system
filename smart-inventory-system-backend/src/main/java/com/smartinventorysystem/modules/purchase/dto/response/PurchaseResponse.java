package com.smartinventorysystem.modules.purchase.dto.response;

import com.smartinventorysystem.enums.PurchaseStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseResponse {

    private Integer purchaseId;
    private String purchaseNumber;
    private Integer supplierId;
    private String supplierName;
    private Integer userId;
    private String userName;
    private LocalDateTime purchaseDate;
    private BigDecimal totalAmount;
    private PurchaseStatus status;
    private LocalDateTime createdAt;
    private List<PurchaseItemResponse> items;
}
