package com.smartinventorysystem.modules.unit.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUnitRequest {
    @NotBlank(message = "Unit name is required")
    private String unitName;
}
