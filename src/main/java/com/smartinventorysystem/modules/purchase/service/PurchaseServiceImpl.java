package com.smartinventorysystem.modules.purchase.service;

import com.smartinventorysystem.enums.PurchaseStatus;
import com.smartinventorysystem.exceptions.BadRequestException;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.exceptions.UnauthorizedException;
import com.smartinventorysystem.modules.product.entity.Product;
import com.smartinventorysystem.modules.product.repository.ProductRepository;
import com.smartinventorysystem.modules.productsupplier.repository.ProductSupplierRepository;
import com.smartinventorysystem.modules.purchase.dto.request.CreatePurchaseRequest;
import com.smartinventorysystem.modules.purchase.dto.request.PurchaseItemRequest;
import com.smartinventorysystem.modules.purchase.dto.request.UpdatePurchaseRequest;
import com.smartinventorysystem.modules.purchase.dto.request.UpdatePurchaseStatusRequest;
import com.smartinventorysystem.modules.purchase.dto.response.PurchaseResponse;
import com.smartinventorysystem.modules.purchase.entity.Purchase;
import com.smartinventorysystem.modules.purchase.entity.PurchaseDetail;
import com.smartinventorysystem.modules.purchase.mapper.PurchaseMapper;
import com.smartinventorysystem.modules.purchase.repository.PurchaseRepository;
import com.smartinventorysystem.modules.supplier.entity.Supplier;
import com.smartinventorysystem.modules.supplier.repository.SupplierRepository;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.modules.user.service.UserService;
import com.smartinventorysystem.enums.MovementType;
import com.smartinventorysystem.modules.stockmovement.entity.StockMovement;
import com.smartinventorysystem.modules.stockmovement.repository.StockMovementRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductSupplierRepository productSupplierRepository;
    private final StockMovementRepository stockMovementRepository;
    private final UserService userService;
    private final PurchaseMapper purchaseMapper;
    private final Clock clock;

    private static final String PURCHASE_NOT_FOUND = "Purchase not found with ID: ";

    @Override
    @Transactional
    public PurchaseResponse createPurchase(CreatePurchaseRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new UnauthorizedException("Unauthorized access");
        }

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with ID: " + request.getSupplierId()));

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setUserID(user.getUserID());
        purchase.setPurchaseDate(request.getPurchaseDate());
        purchase.setStatus(PurchaseStatus.PENDING);

        // Generate unique purchase number (length <= 30)
        String purchaseNumber = "PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        while (purchaseRepository.findByPurchaseNumber(purchaseNumber).isPresent()) {
            purchaseNumber = "PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        purchase.setPurchaseNumber(purchaseNumber);
        purchase.setCreatedAt(LocalDateTime.now(clock));
        purchase.setUpdatedAt(LocalDateTime.now(clock));

        List<PurchaseDetail> details = new ArrayList<>();
        for (PurchaseItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + itemReq.getProductId()));

            validateProductSupplier(product, supplier);

            PurchaseDetail detail = new PurchaseDetail();
            detail.setPurchase(purchase);
            detail.setProduct(product);
            detail.setQuantity(itemReq.getQuantity());
            detail.setUnitPrice(itemReq.getUnitPrice());
            detail.setSubTotal(itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            details.add(detail);
        }
        purchase.getPurchaseDetails().addAll(details);

        BigDecimal totalAmount = purchase.getPurchaseDetails().stream()
                .map(PurchaseDetail::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        purchase.setTotalAmount(totalAmount);

        Purchase savedPurchase = purchaseRepository.save(purchase);

        PurchaseResponse response = purchaseMapper.toResponse(savedPurchase);
        response.setUserName(user.getFullName());

        return response;
    }

    @Override
    @Transactional
    public PurchaseResponse updatePurchase(Integer purchaseId, UpdatePurchaseRequest request) {
        Purchase purchase = purchaseRepository.findByIdWithDetails(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException(PURCHASE_NOT_FOUND + purchaseId));

        if (purchase.getStatus() != PurchaseStatus.PENDING) {
            throw new BadRequestException("Only pending purchases can be updated.");
        }

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with ID: " + request.getSupplierId()));

        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(request.getPurchaseDate());

        // Update purchase details
        purchase.getPurchaseDetails().clear();

        List<PurchaseDetail> details = new ArrayList<>();
        for (PurchaseItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + itemReq.getProductId()));

            validateProductSupplier(product, supplier);

            PurchaseDetail detail = new PurchaseDetail();
            detail.setPurchase(purchase);
            detail.setProduct(product);
            detail.setQuantity(itemReq.getQuantity());
            detail.setUnitPrice(itemReq.getUnitPrice());
            detail.setSubTotal(itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            details.add(detail);
        }
        purchase.getPurchaseDetails().addAll(details);

        BigDecimal totalAmount = purchase.getPurchaseDetails().stream()
                .map(PurchaseDetail::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        purchase.setTotalAmount(totalAmount);
        purchase.setUpdatedAt(LocalDateTime.now(clock));

        Purchase updatedPurchase = purchaseRepository.save(purchase);

        PurchaseResponse response = purchaseMapper.toResponse(updatedPurchase);

        response.setUserName(
                userService.getUserFullName(purchase.getUserID())
        );

        return response;
    }

    @Override
    @Transactional
    public void deletePurchase(Integer purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException(PURCHASE_NOT_FOUND + purchaseId));

        if (purchase.getStatus() == PurchaseStatus.RECEIVED) {
            throw new BadRequestException("Cannot delete a received purchase.");
        }

        purchaseRepository.delete(purchase);
    }

    @Override
    public PurchaseResponse getPurchaseById(Integer purchaseId) {
        Purchase purchase = purchaseRepository.findByIdWithDetails(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException(PURCHASE_NOT_FOUND + purchaseId));

        PurchaseResponse response = purchaseMapper.toResponse(purchase);

        response.setUserName(
                userService.getUserFullName(purchase.getUserID())
        );

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseResponse> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAllWithDetails();
        List<PurchaseResponse> responses = purchaseMapper.toResponseList(purchases);

        List<Integer> userIds = responses.stream()
                .map(PurchaseResponse::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        Map<Integer, String> userNames = userService.getUserFullNames(userIds);

        responses.forEach(response ->
                response.setUserName(userNames.get(response.getUserId())));
        return responses;
    }

    @Override
    @Transactional
    public PurchaseResponse updatePurchaseStatus(Integer purchaseId, UpdatePurchaseStatusRequest request) {
        Purchase purchase = purchaseRepository.findByIdWithDetails(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException(PURCHASE_NOT_FOUND+ purchaseId));

        PurchaseStatus oldStatus = purchase.getStatus();
        PurchaseStatus newStatus = request.getStatus();

        if (oldStatus == newStatus) {
            return purchaseMapper.toResponse(purchase);
        }

        // Adjust stock quantities based on status transitions
        if (newStatus == PurchaseStatus.RECEIVED) {
            for (PurchaseDetail detail : purchase.getPurchaseDetails()) {
                Product product = detail.getProduct();
                int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
                product.setStockQuantity(currentStock + detail.getQuantity());
                productRepository.save(product);
                recordStockMovement(
                        product,
                        detail.getQuantity(),
                        MovementType.PURCHASE,
                        purchase.getUserID(),
                        "Stock received from purchase " + purchase.getPurchaseNumber()
                );
            }
        } else if (oldStatus == PurchaseStatus.RECEIVED) {
            for (PurchaseDetail detail : purchase.getPurchaseDetails()) {
                Product product = detail.getProduct();
                int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
                product.setStockQuantity(currentStock - detail.getQuantity());
                productRepository.save(product);
                recordStockMovement(
                        product,
                        detail.getQuantity(),
                        MovementType.ADJUSTMENT,
                        purchase.getUserID(),
                        "Purchase receipt reversed for " + purchase.getPurchaseNumber()
                );
            }
        }

        purchase.setStatus(newStatus);
        purchase.setUpdatedAt(LocalDateTime.now(clock));

        Purchase updatedPurchase = purchaseRepository.save(purchase);

        PurchaseResponse response = purchaseMapper.toResponse(updatedPurchase);

        response.setUserName(
                userService.getUserFullName(purchase.getUserID())
        );

        return response;
    }

    @Override
    public List<PurchaseResponse> getPurchasesBySupplier(Integer supplierId) {

        if (!supplierRepository.existsById(supplierId)) {
            throw new ResourceNotFoundException(
                    "Supplier not found with ID: " + supplierId
            );
        }

        List<Purchase> purchases =
                purchaseRepository.findBySupplierWithDetails(supplierId);

        List<PurchaseResponse> responses =
                purchaseMapper.toResponseList(purchases);

        List<Integer> userIds = responses.stream()
                .map(PurchaseResponse::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        Map<Integer, String> userNames = userService.getUserFullNames(userIds);

        responses.forEach(response ->
                response.setUserName(userNames.get(response.getUserId()))
        );

        return responses;
    }

    private void validateProductSupplier(Product product, Supplier supplier) {
        if (!productSupplierRepository.existsByProductProductIdAndSupplierSupplierId(
                product.getProductId(),
                supplier.getSupplierId()
        )) {
            throw new BadRequestException(
                    "Supplier '" + supplier.getSupplierName() +
                            "' is not assigned to product '" +
                            product.getProductName() + "'"
            );
        }
    }

    private void recordStockMovement(Product product,
                                     Integer quantity,
                                     MovementType movementType,
                                     Integer userId,
                                     String remarks) {
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setUserID(userId);
        movement.setMovementType(movementType);
        movement.setQuantity(quantity);
        movement.setMovementDate(LocalDateTime.now(clock));
        movement.setRemarks(remarks);
        stockMovementRepository.save(movement);
    }
}

