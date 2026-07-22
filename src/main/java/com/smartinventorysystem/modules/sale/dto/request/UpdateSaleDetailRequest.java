package com.smartinventorysystem.modules.sale.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSaleDetailRequest {
    @NotNull(message = "Product ID is required.")
    private Integer productId;
    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be greater than zero.")
    private Integer quantity;
}