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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductSupplierRepository productSupplierRepository;
    private final PurchaseMapper purchaseMapper;

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
        purchase.setCreatedAt(LocalDateTime.now());
        purchase.setUpdatedAt(LocalDateTime.now());

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
        return purchaseMapper.toResponse(savedPurchase);
    }

    @Override
    @Transactional
    public PurchaseResponse updatePurchase(Integer purchaseId, UpdatePurchaseRequest request) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with ID: " + purchaseId));

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
        purchase.setUpdatedAt(LocalDateTime.now());

        Purchase updatedPurchase = purchaseRepository.save(purchase);
        return purchaseMapper.toResponse(updatedPurchase);
    }

    @Override
    @Transactional
    public void deletePurchase(Integer purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with ID: " + purchaseId));

        if (purchase.getStatus() == PurchaseStatus.RECEIVED) {
            throw new BadRequestException("Cannot delete a received purchase.");
        }

        purchaseRepository.delete(purchase);
    }

    @Override
    public PurchaseResponse getPurchaseById(Integer purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with ID: " + purchaseId));
        return purchaseMapper.toResponse(purchase);
    }

    @Override
    public List<PurchaseResponse> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return purchaseMapper.toResponseList(purchases);
    }

    @Override
    @Transactional
    public PurchaseResponse updatePurchaseStatus(Integer purchaseId, UpdatePurchaseStatusRequest request) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with ID: " + purchaseId));

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
            }
        } else if (oldStatus == PurchaseStatus.RECEIVED) {
            for (PurchaseDetail detail : purchase.getPurchaseDetails()) {
                Product product = detail.getProduct();
                int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
                product.setStockQuantity(currentStock - detail.getQuantity());
                productRepository.save(product);
            }
        }

        purchase.setStatus(newStatus);
        purchase.setUpdatedAt(LocalDateTime.now());

        Purchase updatedPurchase = purchaseRepository.save(purchase);
        return purchaseMapper.toResponse(updatedPurchase);
    }

    @Override
    public List<PurchaseResponse> getPurchasesBySupplier(Integer supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new ResourceNotFoundException("Supplier not found with ID: " + supplierId);
        }
        List<Purchase> purchases = purchaseRepository.findBySupplierSupplierId(supplierId);
        return purchaseMapper.toResponseList(purchases);
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
}

