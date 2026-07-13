package com.smartinventorysystem.modules.purchase.service;

import com.smartinventorysystem.modules.purchase.dto.request.CreatePurchaseRequest;
import com.smartinventorysystem.modules.purchase.dto.request.UpdatePurchaseRequest;
import com.smartinventorysystem.modules.purchase.dto.request.UpdatePurchaseStatusRequest;
import com.smartinventorysystem.modules.purchase.dto.response.PurchaseResponse;

import java.util.List;

public interface PurchaseService {

    PurchaseResponse createPurchase(CreatePurchaseRequest request);

    PurchaseResponse updatePurchase(Integer purchaseId, UpdatePurchaseRequest request);

    void deletePurchase(Integer purchaseId);

    PurchaseResponse getPurchaseById(Integer purchaseId);

    List<PurchaseResponse> getAllPurchases();

    PurchaseResponse updatePurchaseStatus(Integer purchaseId,
                                          UpdatePurchaseStatusRequest request);

    List<PurchaseResponse> getPurchasesBySupplier(Integer supplierId);
}