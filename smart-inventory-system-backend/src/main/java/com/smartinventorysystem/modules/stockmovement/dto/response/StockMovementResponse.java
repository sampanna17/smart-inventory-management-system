package com.smartinventorysystem.modules.stockmovement.dto.response;

import com.smartinventorysystem.enums.MovementType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StockMovementResponse {
    private Integer movementId;
    private Integer productId;
    private String productName;
    private Integer userId;
    private String userName;
    private MovementType movementType;
    private Integer quantity;
    private LocalDateTime movementDate;
    private String remarks;
}