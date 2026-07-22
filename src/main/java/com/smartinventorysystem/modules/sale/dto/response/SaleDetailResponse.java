package com.smartinventorysystem.modules.sale.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SaleDetailResponse {
    private Integer saleDetailId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
}