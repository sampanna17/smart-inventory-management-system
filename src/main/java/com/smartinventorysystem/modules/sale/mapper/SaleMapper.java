package com.smartinventorysystem.modules.sale.mapper;

import com.smartinventorysystem.modules.sale.dto.response.SaleDetailResponse;
import com.smartinventorysystem.modules.sale.dto.response.SaleResponse;
import com.smartinventorysystem.modules.sale.dto.response.SaleSummaryResponse;
import com.smartinventorysystem.modules.sale.entity.Sale;
import com.smartinventorysystem.modules.sale.entity.SaleDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper
public interface SaleMapper {

    @Mapping(source = "sale.saleID", target = "saleId")
    @Mapping(source = "sale.customer.customerID", target = "customerId")
    @Mapping(source = "sale.customer.customerName", target = "customerName")
    @Mapping(source = "sale.userID", target = "userId")
    @Mapping(target = "userName", ignore = true)
    @Mapping(source = "details", target = "items")
    SaleResponse toResponse(Sale sale, List<SaleDetail> details);

    @Mapping(source = "saleID", target = "saleId")
    @Mapping(source = "customer.customerID", target = "customerId")
    @Mapping(source = "customer.customerName", target = "customerName")
    @Mapping(source = "userID", target = "userId")
    @Mapping(target = "userName", ignore = true)
    SaleSummaryResponse toSummaryResponse(Sale sale);

    List<SaleSummaryResponse> toSummaryResponseList(List<Sale> sales);

    @Mapping(source = "saleDetailID", target = "saleDetailId")
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    SaleDetailResponse toDetailResponse(SaleDetail saleDetail);

    List<SaleDetailResponse> toDetailResponseList(List<SaleDetail> saleDetails);
}