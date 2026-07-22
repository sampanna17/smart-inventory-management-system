package com.smartinventorysystem.modules.sale.service;

import com.smartinventorysystem.enums.SaleStatus;
import com.smartinventorysystem.modules.sale.dto.request.CreateSaleRequest;
import com.smartinventorysystem.modules.sale.dto.request.UpdateSaleRequest;
import com.smartinventorysystem.modules.sale.dto.request.UpdateSaleStatusRequest;
import com.smartinventorysystem.modules.sale.dto.response.SaleResponse;
import com.smartinventorysystem.modules.sale.dto.response.SaleSummaryResponse;
import java.util.List;

public interface SaleService {

    SaleResponse createSale(CreateSaleRequest request);

    SaleResponse updateSale(Integer saleId, UpdateSaleRequest request);

    void deleteSale(Integer saleId);

    SaleResponse updateStatus(Integer saleId, UpdateSaleStatusRequest request);

    SaleResponse getSale(Integer saleId);

    List<SaleSummaryResponse> getAllSales();

    List<SaleSummaryResponse> getSalesByCustomer(Integer customerId);

    List<SaleSummaryResponse> getSalesByStatus(SaleStatus status);
}