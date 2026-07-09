package com.smartinventorysystem.modules.product.dto.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    private Integer categoryId;

    private Integer unitId;

    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String productName;

    private String description;

    @DecimalMin(value = "0.0", message = "Cost price must be 0 or greater")
    private BigDecimal costPrice;

    @DecimalMin(value = "0.0", message = "Selling price must be 0 or greater")
    private BigDecimal sellingPrice;

    @Min(value = 0, message = "Reorder level must be 0 or greater")
    private Integer reorderLevel;
}
