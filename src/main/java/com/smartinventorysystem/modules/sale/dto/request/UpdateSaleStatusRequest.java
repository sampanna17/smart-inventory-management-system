package com.smartinventorysystem.modules.sale.dto.request;

import com.smartinventorysystem.enums.SaleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSaleStatusRequest {
    @NotNull(message = "Status is required.")
    private SaleStatus status;
}