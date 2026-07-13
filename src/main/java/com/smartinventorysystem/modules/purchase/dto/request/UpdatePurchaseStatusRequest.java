package com.smartinventorysystem.modules.purchase.dto.request;

import com.smartinventorysystem.enums.PurchaseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePurchaseStatusRequest {

    @NotNull(message = "Status is required.")
    private PurchaseStatus status;
}