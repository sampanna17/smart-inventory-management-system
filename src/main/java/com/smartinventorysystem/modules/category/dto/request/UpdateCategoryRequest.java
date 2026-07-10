package com.smartinventorysystem.modules.category.dto.request;

import lombok.Data;

@Data
public class UpdateCategoryRequest {

    private String categoryName;
    private String description;
}
