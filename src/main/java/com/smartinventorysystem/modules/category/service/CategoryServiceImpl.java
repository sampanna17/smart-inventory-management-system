package com.smartinventorysystem.modules.category.service;

import com.smartinventorysystem.constants.MessageConstants;
import com.smartinventorysystem.exceptions.DuplicateCategoryException;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.modules.category.dto.request.CreateCategoryRequest;
import com.smartinventorysystem.modules.category.dto.request.UpdateCategoryRequest;
import com.smartinventorysystem.modules.category.dto.response.CategoryResponse;
import com.smartinventorysystem.modules.category.entity.Category;
import com.smartinventorysystem.modules.category.mapper.CategoryMapper;
import com.smartinventorysystem.modules.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final Clock clock;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {

        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new DuplicateCategoryException("Category already exists with name: " + request.getCategoryName());
        }

        Category category = categoryMapper.toEntity(request);
        category.setCreatedAt(LocalDateTime.now(clock));

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CATEGORY_NOT_FOUND));

        if (request.getCategoryName() != null && !request.getCategoryName().isBlank()) {

            if (categoryRepository.existsByCategoryNameAndCategoryIdNot(
                    request.getCategoryName(),
                    id
            )) {
                throw new DuplicateCategoryException(
                        "Category already exists with name: " + request.getCategoryName()
                );
            }

            category.setCategoryName(request.getCategoryName());
        }

        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }

        category.setUpdatedAt(LocalDateTime.now(clock));

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Integer id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CATEGORY_NOT_FOUND));

        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CATEGORY_NOT_FOUND));

        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }
}