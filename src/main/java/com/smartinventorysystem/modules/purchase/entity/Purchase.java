package com.smartinventorysystem.modules.purchase.entity;

import com.smartinventorysystem.enums.PurchaseStatus;
import com.smartinventorysystem.modules.supplier.entity.Supplier;
import com.smartinventorysystem.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Purchases")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseID;

    private String purchaseNumber;

    @ManyToOne
    @JoinColumn(name = "supplierID")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    private LocalDateTime purchaseDate;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    private LocalDateTime createdAt;
}
