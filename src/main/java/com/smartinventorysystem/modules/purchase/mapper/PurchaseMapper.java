package com.smartinventorysystem.modules.purchase.mapper;

import com.smartinventorysystem.modules.purchase.dto.response.PurchaseItemResponse;
import com.smartinventorysystem.modules.purchase.dto.response.PurchaseResponse;
import com.smartinventorysystem.modules.purchase.entity.Purchase;
import com.smartinventorysystem.modules.purchase.entity.PurchaseDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    @Mapping(source = "purchaseId", target = "purchaseId")
    @Mapping(source = "supplier.supplierId", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierName")
    @Mapping(source = "user.userID", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "purchaseDetails", target = "items")
    PurchaseResponse toResponse(Purchase purchase);

    List<PurchaseResponse> toResponseList(List<Purchase> purchases);

    @Mapping(source = "purchaseDetailId", target = "purchaseDetailId")
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    PurchaseItemResponse toItemResponse(PurchaseDetail purchaseDetail);

    List<PurchaseItemResponse> toItemResponseList(List<PurchaseDetail> purchaseDetails);
}