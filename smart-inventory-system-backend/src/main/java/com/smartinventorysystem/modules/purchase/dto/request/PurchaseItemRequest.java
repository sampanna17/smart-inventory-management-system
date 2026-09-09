package com.smartinventorysystem.modules.purchase.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseItemRequest {

    @NotNull(message = "Product ID is required.")
    private Integer productId;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be greater than zero.")
    private Integer quantity;

    @NotNull(message = "Unit price is required.")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than zero.")
    private BigDecimal unitPrice;
}
