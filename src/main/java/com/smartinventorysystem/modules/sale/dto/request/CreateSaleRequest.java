package com.smartinventorysystem.modules.sale.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateSaleRequest {

    private Integer customerId; // Nullable/Optional

    @NotNull(message = "Sale date is required.")
    private LocalDateTime saleDate;

    @Valid
    @NotEmpty(message = "Sale must contain at least one item.")
    private List<CreateSaleDetailRequest> items;
}
