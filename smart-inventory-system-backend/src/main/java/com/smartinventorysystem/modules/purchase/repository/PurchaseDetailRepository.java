package com.smartinventorysystem.modules.purchase.repository;

import com.smartinventorysystem.modules.purchase.entity.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Integer> {

    List<PurchaseDetail> findByPurchasePurchaseId(Integer purchaseId);
}
