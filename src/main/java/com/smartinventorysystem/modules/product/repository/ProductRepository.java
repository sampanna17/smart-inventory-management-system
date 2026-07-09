package com.smartinventorysystem.modules.product.repository;

import com.smartinventorysystem.modules.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByProductName(String productName);
    boolean existsByProductNameAndProductIdNot(String productName, Integer productId);
}