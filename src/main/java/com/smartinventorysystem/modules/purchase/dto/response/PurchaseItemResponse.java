package com.smartinventorysystem.modules.purchase.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseItemResponse {

    private Integer purchaseDetailId;

    private Integer productId;

    private String productName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subTotal;
}