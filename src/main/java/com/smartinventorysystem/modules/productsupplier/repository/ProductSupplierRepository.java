package com.smartinventorysystem.modules.productsupplier.repository;

import com.smartinventorysystem.modules.productsupplier.entity.ProductSupplier;
import com.smartinventorysystem.modules.productsupplier.entity.ProductSupplierId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, ProductSupplierId> {

    List<ProductSupplier> findByProductProductId(Integer productId);

    List<ProductSupplier> findBySupplierSupplierId(Integer supplierId);

    boolean existsByProductProductIdAndSupplierSupplierId(Integer productId, Integer supplierId);

    void deleteByProductProductIdAndSupplierSupplierId(Integer productId, Integer supplierId);
}
