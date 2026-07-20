package com.smartinventorysystem.modules.sale.entity;

import com.smartinventorysystem.enums.SaleStatus;
import com.smartinventorysystem.modules.customer.entity.Customer;
import com.smartinventorysystem.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Sales")
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saleID;

    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer customer;

    private Integer userID;

    private LocalDateTime saleDate;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    private LocalDateTime createdAt;
}