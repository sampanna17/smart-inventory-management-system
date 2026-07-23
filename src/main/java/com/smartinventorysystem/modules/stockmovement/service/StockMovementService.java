package com.smartinventorysystem.modules.stockmovement.service;

import com.smartinventorysystem.enums.MovementType;
import com.smartinventorysystem.modules.product.entity.Product;
import com.smartinventorysystem.modules.stockmovement.dto.request.CreateStockMovementRequest;
import com.smartinventorysystem.modules.stockmovement.dto.response.StockMovementResponse;

import java.util.List;

public interface StockMovementService {

    StockMovementResponse createStockMovement(CreateStockMovementRequest request);

    StockMovementResponse getStockMovementById(Integer movementId);

    List<StockMovementResponse> getAllStockMovements();

    List<StockMovementResponse> getMovementsByProduct(Integer productId);

    List<StockMovementResponse> getMovementsByUser(Integer userId);

    List<StockMovementResponse> getMovementsByMovementType(String movementType);

    void deleteStockMovement(Integer movementId);

    void recordMovement(Product product,
                        Integer quantity,
                        MovementType movementType,
                        Integer userId,
                        String remarks);
}