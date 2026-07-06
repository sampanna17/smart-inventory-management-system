package com.smartinventorysystem.modules.category.mapper;

import com.smartinventorysystem.modules.category.dto.Response.CategoryResponse;
import com.smartinventorysystem.modules.category.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);
}