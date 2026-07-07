package com.smartinventorysystem.modules.unit.dto.Response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UnitResponse {
    private Integer unitId;
    private String unitName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
