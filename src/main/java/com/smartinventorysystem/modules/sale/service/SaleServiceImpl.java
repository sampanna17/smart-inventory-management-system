package com.smartinventorysystem.modules.sale.service;

import com.smartinventorysystem.constants.MessageConstants;
import com.smartinventorysystem.enums.SaleStatus;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.exceptions.InsufficientStockException;
import com.smartinventorysystem.exceptions.InvalidSaleStatusException;
import com.smartinventorysystem.exceptions.UnauthorizedException;
import com.smartinventorysystem.modules.customer.entity.Customer;
import com.smartinventorysystem.modules.customer.repository.CustomerRepository;
import com.smartinventorysystem.modules.product.entity.Product;
import com.smartinventorysystem.modules.product.repository.ProductRepository;
import com.smartinventorysystem.modules.sale.dto.request.CreateSaleDetailRequest;
import com.smartinventorysystem.modules.sale.dto.request.CreateSaleRequest;
import com.smartinventorysystem.modules.sale.dto.request.UpdateSaleDetailRequest;
import com.smartinventorysystem.modules.sale.dto.request.UpdateSaleRequest;
import com.smartinventorysystem.modules.sale.dto.request.UpdateSaleStatusRequest;
import com.smartinventorysystem.modules.sale.dto.response.SaleResponse;
import com.smartinventorysystem.modules.sale.dto.response.SaleSummaryResponse;
import com.smartinventorysystem.modules.sale.entity.Sale;
import com.smartinventorysystem.modules.sale.entity.SaleDetail;
import com.smartinventorysystem.modules.sale.mapper.SaleMapper;
import com.smartinventorysystem.modules.sale.repository.SaleRepository;
import com.smartinventorysystem.modules.sale.repository.SaleDetailRepository;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.security.SecureRandom;


@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final SaleMapper saleMapper;
    private final Clock clock;

    @Override
    @Transactional
    public SaleResponse createSale(CreateSaleRequest request) {
        User user = getAuthenticatedUser();
        Customer customer = getCustomerIfPresent(request.getCustomerId());

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setUserID(user.getUserID());
        sale.setSaleDate(request.getSaleDate());
        sale.setStatus(SaleStatus.COMPLETED);
        sale.setInvoiceNumber(generateInvoiceNumber());
        sale.setCreatedAt(LocalDateTime.now(clock));

        List<SaleDetail> details = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CreateSaleDetailRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND_MSG + itemReq.getProductId()));

            validateAndDeductStock(product, itemReq.getQuantity());

            SaleDetail detail = new SaleDetail();
            detail.setSale(sale);
            detail.setProduct(product);
            detail.setQuantity(itemReq.getQuantity());
            detail.setUnitPrice(product.getSellingPrice());

            BigDecimal subTotal = product.getSellingPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            detail.setSubTotal(subTotal);

            totalAmount = totalAmount.add(subTotal);
            details.add(detail);
        }

        sale.setTotalAmount(totalAmount);
        Sale savedSale = saleRepository.save(sale);
        List<SaleDetail> savedDetails = saleDetailRepository.saveAll(details);

        SaleResponse response = saleMapper.toResponse(savedSale, savedDetails);
        response.setUserName(user.getFullName());
        return response;
    }

    @Override
    @Transactional
    public SaleResponse updateSale(Integer saleId, UpdateSaleRequest request) {
        Sale sale = saleRepository.findByIdWithCustomer(saleId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.SALE_NOT_FOUND_MSG + saleId));

        if (sale.getStatus() != SaleStatus.COMPLETED) {
            throw new InvalidSaleStatusException("Only COMPLETED sales can be edited.");
        }

        Customer customer = getCustomerIfPresent(request.getCustomerId());
        sale.setCustomer(customer);
        sale.setSaleDate(request.getSaleDate());

        // Restore previous stock
        List<SaleDetail> oldDetails = saleDetailRepository.findBySaleIdWithProduct(saleId);
        restoreStock(oldDetails);

        List<SaleDetail> newDetails = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (UpdateSaleDetailRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND_MSG + itemReq.getProductId()));

            validateAndDeductStock(product, itemReq.getQuantity());

            SaleDetail detail = new SaleDetail();
            detail.setSale(sale);
            detail.setProduct(product);
            detail.setQuantity(itemReq.getQuantity());
            detail.setUnitPrice(product.getSellingPrice());

            BigDecimal subTotal = product.getSellingPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            detail.setSubTotal(subTotal);

            totalAmount = totalAmount.add(subTotal);
            newDetails.add(detail);
        }

        sale.setTotalAmount(totalAmount);

        // Remove old details and save new details
        saleDetailRepository.deleteBySaleId(saleId);
        List<SaleDetail> savedDetails = saleDetailRepository.saveAll(newDetails);

        Sale updatedSale = saleRepository.save(sale);

        SaleResponse response = saleMapper.toResponse(updatedSale, savedDetails);
        response.setUserName(userService.getUserFullName(updatedSale.getUserID()));
        return response;
    }

    @Override
    @Transactional
    public void deleteSale(Integer saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.SALE_NOT_FOUND_MSG + saleId));

        List<SaleDetail> details = saleDetailRepository.findBySaleIdWithProduct(saleId);

        if (sale.getStatus() == SaleStatus.COMPLETED) {
            restoreStock(details);
        }

        saleDetailRepository.deleteBySaleId(saleId);
        saleRepository.delete(sale);
    }

    @Override
    @Transactional
    public SaleResponse updateStatus(Integer saleId, UpdateSaleStatusRequest request) {
        Sale sale = saleRepository.findByIdWithCustomer(saleId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.SALE_NOT_FOUND_MSG + saleId));

        SaleStatus oldStatus = sale.getStatus();
        SaleStatus newStatus = request.getStatus();

        if (oldStatus == newStatus) {
            List<SaleDetail> details = saleDetailRepository.findBySaleIdWithProduct(saleId);
            SaleResponse response = saleMapper.toResponse(sale, details);
            response.setUserName(userService.getUserFullName(sale.getUserID()));
            return response;
        }

        List<SaleDetail> details = saleDetailRepository.findBySaleIdWithProduct(saleId);

        // Transition Rules
        if (oldStatus == SaleStatus.COMPLETED && (newStatus == SaleStatus.CANCELLED || newStatus == SaleStatus.REFUNDED)) {
            restoreStock(details);
        } else if ((oldStatus == SaleStatus.CANCELLED || oldStatus == SaleStatus.REFUNDED) && newStatus == SaleStatus.COMPLETED) {
            deductStock(details);
        }

        sale.setStatus(newStatus);
        Sale updatedSale = saleRepository.save(sale);

        SaleResponse response = saleMapper.toResponse(updatedSale, details);
        response.setUserName(userService.getUserFullName(updatedSale.getUserID()));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse getSale(Integer saleId) {
        Sale sale = saleRepository.findByIdWithCustomer(saleId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.SALE_NOT_FOUND_MSG + saleId));

        List<SaleDetail> details = saleDetailRepository.findBySaleIdWithProduct(saleId);

        SaleResponse response = saleMapper.toResponse(sale, details);
        response.setUserName(userService.getUserFullName(sale.getUserID()));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleSummaryResponse> getAllSales() {
        List<Sale> sales = saleRepository.findAllWithCustomer();
        return mapToSummaryResponses(sales);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleSummaryResponse> getSalesByCustomer(Integer customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
        List<Sale> sales = saleRepository.findByCustomerWithCustomer(customerId);
        return mapToSummaryResponses(sales);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleSummaryResponse> getSalesByStatus(SaleStatus status) {
        List<Sale> sales = saleRepository.findByStatusWithCustomer(status);
        return mapToSummaryResponses(sales);
    }

    // ==========================================
    // PRIVATE HELPER METHODS
    // ==========================================

    private User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new UnauthorizedException("Unauthorized access");
        }
        return user;
    }

    private Customer getCustomerIfPresent(Integer customerId) {
        if (customerId == null) {
            return null;
        }
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
    }

    private String generateInvoiceNumber() {

        String dateStr = DateTimeFormatter.ofPattern("yyyyMMdd")
                .format(LocalDateTime.now(clock));

        String invoiceNumber;

        do {
            String randomSuffix = String.format("%05d", RANDOM.nextInt(100000));
            invoiceNumber = "INV-" + dateStr + "-" + randomSuffix;

        } while (saleRepository.findByInvoiceNumber(invoiceNumber).isPresent());

        return invoiceNumber;
    }

    private void validateAndDeductStock(Product product, int quantity) {
        int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
        if (currentStock < quantity) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getProductName() + ". Available: " + currentStock + ", Requested: " + quantity);
        }
        product.setStockQuantity(currentStock - quantity);
        productRepository.save(product);
    }

    private void restoreStock(List<SaleDetail> details) {
        for (SaleDetail detail : details) {
            Product product = detail.getProduct();
            int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
            product.setStockQuantity(currentStock + detail.getQuantity());
            productRepository.save(product);
        }
    }

    private void deductStock(List<SaleDetail> details) {
        // Validate stock first
        for (SaleDetail detail : details) {
            Product product = detail.getProduct();
            int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
            if (currentStock < detail.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getProductName() + ". Available: " + currentStock + ", Requested: " + detail.getQuantity());
            }
        }
        // Deduct stock if all are valid
        for (SaleDetail detail : details) {
            Product product = detail.getProduct();
            int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
            product.setStockQuantity(currentStock - detail.getQuantity());
            productRepository.save(product);
        }
    }

    private List<SaleSummaryResponse> mapToSummaryResponses(List<Sale> sales) {
        List<SaleSummaryResponse> responses = saleMapper.toSummaryResponseList(sales);
        List<Integer> userIds = responses.stream()
                .map(SaleSummaryResponse::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (!userIds.isEmpty()) {
            Map<Integer, String> userNames = userService.getUserFullNames(userIds);
            responses.forEach(response -> response.setUserName(userNames.get(response.getUserId())));
        }
        return responses;
    }
}