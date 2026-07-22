package com.smartinventorysystem.modules.sale.dto.response;

import com.smartinventorysystem.enums.SaleStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleResponse {
    private Integer saleId;
    private String invoiceNumber;
    private Integer customerId;
    private String customerName;
    private Integer userId;
    private String userName;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private SaleStatus status;
    private LocalDateTime createdAt;
    private List<SaleDetailResponse> items;
}