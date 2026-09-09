package com.smartinventorysystem.modules.product.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponse {

    private Integer productId;
    private String productName;
    private String description;

    private BigDecimal costPrice;
    private BigDecimal sellingPrice;

    private Integer stockQuantity;
    private Integer reorderLevel;

    private Integer categoryId;
    private String categoryName;

    private Integer unitId;
    private String unitName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
