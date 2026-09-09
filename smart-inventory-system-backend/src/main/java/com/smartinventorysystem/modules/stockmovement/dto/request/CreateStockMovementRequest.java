package com.smartinventorysystem.modules.stockmovement.dto.request;

import com.smartinventorysystem.enums.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateStockMovementRequest {

    @NotNull(message = "Product ID is required.")
    private Integer productId;

    @NotNull(message = "Movement type is required.")
    private MovementType movementType;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be greater than zero.")
    private Integer quantity;

    @Size(max = 255, message = "Remarks cannot exceed 255 characters.")
    private String remarks;
}
