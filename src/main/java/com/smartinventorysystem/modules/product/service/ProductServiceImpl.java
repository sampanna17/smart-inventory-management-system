package com.smartinventorysystem.modules.product.service;

import com.smartinventorysystem.exceptions.DuplicateProductException;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.modules.category.entity.Category;
import com.smartinventorysystem.modules.category.repository.CategoryRepository;
import com.smartinventorysystem.modules.product.dto.request.CreateProductRequest;
import com.smartinventorysystem.modules.product.dto.request.UpdateProductRequest;
import com.smartinventorysystem.modules.product.dto.response.ProductResponse;
import com.smartinventorysystem.modules.product.entity.Product;
import com.smartinventorysystem.modules.product.mapper.ProductMapper;
import com.smartinventorysystem.modules.product.repository.ProductRepository;
import com.smartinventorysystem.modules.unit.entity.Unit;
import com.smartinventorysystem.modules.unit.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UnitRepository unitRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        if (productRepository.existsByProductName(request.getProductName())) {
            throw new DuplicateProductException(
                    "Product already exists with name: " + request.getProductName());
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Unit unit = unitRepository.findById(request.getUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));

        Product product = productMapper.toEntity(request);

        product.setCategory(category);
        product.setUnit(unit);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Integer productId, UpdateProductRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            product.setCategory(category);
        }

        if (request.getUnitId() != null) {
            Unit unit = unitRepository.findById(request.getUnitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));

            product.setUnit(unit);
        }

        if (request.getProductName() != null
                && !request.getProductName().isBlank()) {

            if (productRepository.existsByProductNameAndProductIdNot(
                    request.getProductName(), productId)) {

                throw new DuplicateProductException(
                        "Product already exists with name: " + request.getProductName());
            }

            product.setProductName(request.getProductName());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        if (request.getCostPrice() != null) {
            product.setCostPrice(request.getCostPrice());
        }

        if (request.getSellingPrice() != null) {
            product.setSellingPrice(request.getSellingPrice());
        }

        if (request.getReorderLevel() != null) {
            product.setReorderLevel(request.getReorderLevel());
        }

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    @Override
    public ProductResponse getProductById(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productMapper.toResponseList(productRepository.findAll());
    }
}