package com.smartinventorysystem.modules.category.dto.Request;

import lombok.Data;

@Data
public class UpdateCategoryRequest {

    private String categoryName;
    private String description;
}
