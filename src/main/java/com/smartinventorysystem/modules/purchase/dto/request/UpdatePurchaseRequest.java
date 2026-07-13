package com.smartinventorysystem.modules.purchase.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdatePurchaseRequest {

    @NotNull(message = "Supplier ID is required.")
    private Integer supplierId;

    @NotNull(message = "Purchase Date is required.")
    private LocalDateTime purchaseDate;

    @Valid
    @NotEmpty(message = "Purchase must contain at least one item.")
    private List<PurchaseItemRequest> items;
}
