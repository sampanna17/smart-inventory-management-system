package com.smartinventorysystem.modules.unit.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUnitRequest {
    @NotBlank(message = "Unit name is required")
    @Size(min = 2, max = 20, message = "Unit name must be between 2 and 50 characters")
    private String unitName;
}
