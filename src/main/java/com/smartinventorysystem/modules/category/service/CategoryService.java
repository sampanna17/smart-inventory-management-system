package com.smartinventorysystem.modules.category.service;

import com.smartinventorysystem.modules.category.dto.Request.CreateCategoryRequest;
import com.smartinventorysystem.modules.category.dto.Request.UpdateCategoryRequest;
import com.smartinventorysystem.modules.category.dto.Response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request);

    void deleteCategory(Integer id);

    CategoryResponse getCategoryById(Integer id);

    List<CategoryResponse> getAllCategories();
}