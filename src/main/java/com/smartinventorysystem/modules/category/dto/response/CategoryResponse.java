package com.smartinventorysystem.modules.category.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponse {

    private Integer categoryID;
    private String categoryName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
