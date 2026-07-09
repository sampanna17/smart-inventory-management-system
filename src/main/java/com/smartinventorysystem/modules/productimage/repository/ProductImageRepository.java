package com.smartinventorysystem.modules.productimage.repository;

import com.smartinventorysystem.modules.productimage.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository
        extends JpaRepository<ProductImage, Integer> {

    List<ProductImage> findByProductProductId(Integer productId);
}
