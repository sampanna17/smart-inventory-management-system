package com.smartinventorysystem.modules.stockmovement.service;

import com.smartinventorysystem.enums.MovementType;
import com.smartinventorysystem.exceptions.BadRequestException;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.exceptions.UnauthorizedException;
import com.smartinventorysystem.modules.product.entity.Product;
import com.smartinventorysystem.modules.product.repository.ProductRepository;
import com.smartinventorysystem.modules.stockmovement.dto.request.CreateStockMovementRequest;
import com.smartinventorysystem.modules.stockmovement.dto.response.StockMovementResponse;
import com.smartinventorysystem.modules.stockmovement.entity.StockMovement;
import com.smartinventorysystem.modules.stockmovement.mapper.StockMovementMapper;
import com.smartinventorysystem.modules.stockmovement.repository.StockMovementRepository;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockMovementMapper stockMovementMapper;
    private final Clock clock;

    private static final String STOCK_MOVEMENT_NOT_FOUND = "Stock movement not found with ID: ";

    @Override
    @Transactional
    public StockMovementResponse createStockMovement(CreateStockMovementRequest request) {
        User authenticatedUser = getAuthenticatedUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with ID: " + request.getProductId()
                ));

        validateMovementQuantity(request.getQuantity());
        validateMovementType(request.getMovementType());

        StockMovement stockMovement = new StockMovement();
        stockMovement.setProduct(product);
        stockMovement.setUserID(authenticatedUser.getUserID());
        stockMovement.setMovementType(request.getMovementType());
        stockMovement.setQuantity(request.getQuantity());
        stockMovement.setMovementDate(LocalDateTime.now(clock));
        stockMovement.setRemarks(request.getRemarks());

        StockMovement savedMovement = stockMovementRepository.save(stockMovement);

        StockMovementResponse response = stockMovementMapper.toResponse(savedMovement);
        response.setUserName(authenticatedUser.getFullName());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public StockMovementResponse getStockMovementById(Integer movementId) {
        StockMovement stockMovement = stockMovementRepository.findByIdWithProduct(movementId)
                .orElseThrow(() -> new ResourceNotFoundException(STOCK_MOVEMENT_NOT_FOUND + movementId));

        StockMovementResponse response = stockMovementMapper.toResponse(stockMovement);
        response.setUserName(getUserFullName(stockMovement.getUserID()));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementResponse> getAllStockMovements() {
        List<StockMovement> movements = stockMovementRepository.findAllWithProduct();
        List<StockMovementResponse> responses = stockMovementMapper.toResponseList(movements);

        populateUserNames(responses);
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementResponse> getMovementsByProduct(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with ID: " + productId);
        }

        List<StockMovement> movements = stockMovementRepository.findByProductWithProduct(productId);
        List<StockMovementResponse> responses = stockMovementMapper.toResponseList(movements);

        populateUserNames(responses);
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementResponse> getMovementsByUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        List<StockMovement> movements = stockMovementRepository.findByUserWithProduct(userId);
        List<StockMovementResponse> responses = stockMovementMapper.toResponseList(movements);

        populateUserNames(responses);
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementResponse> getMovementsByMovementType(String movementType) {
        MovementType type = parseMovementType(movementType);

        List<StockMovement> movements = stockMovementRepository.findByMovementTypeWithProduct(type);
        List<StockMovementResponse> responses = stockMovementMapper.toResponseList(movements);

        populateUserNames(responses);
        return responses;
    }

    @Override
    @Transactional
    public void deleteStockMovement(Integer movementId) {
        StockMovement stockMovement = stockMovementRepository.findById(movementId)
                .orElseThrow(() -> new ResourceNotFoundException(STOCK_MOVEMENT_NOT_FOUND + movementId));

        if (stockMovement.getMovementType() == MovementType.PURCHASE
                || stockMovement.getMovementType() == MovementType.SALE) {
            throw new BadRequestException("Purchase and sale stock movements cannot be deleted.");
        }

        stockMovementRepository.delete(stockMovement);
    }

    private User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new UnauthorizedException("Unauthorized access");
        }
        return user;
    }

    private void validateMovementQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero.");
        }
    }

    private void validateMovementType(MovementType movementType) {
        if (movementType == null) {
            throw new BadRequestException("Movement type is required.");
        }
    }

    private MovementType parseMovementType(String movementType) {
        if (movementType == null || movementType.isBlank()) {
            throw new BadRequestException("Movement type is required.");
        }

        try {
            return MovementType.valueOf(movementType.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid movement type: " + movementType);
        }
    }

    private void populateUserNames(List<StockMovementResponse> responses) {
        List<Integer> userIds = responses.stream()
                .map(StockMovementResponse::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (userIds.isEmpty()) {
            return;
        }

        responses.forEach(response -> response.setUserName(getUserFullName(response.getUserId())));
    }

    private String getUserFullName(Integer userId) {
        return userRepository.findById(userId)
                .map(User::getFullName)
                .orElse(null);
    }
}