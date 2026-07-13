package com.smartinventorysystem.modules.purchase.repository;

import com.smartinventorysystem.modules.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    Optional<Purchase> findByPurchaseNumber(String purchaseNumber);

    List<Purchase> findBySupplierSupplierId(Integer supplierId);
}
