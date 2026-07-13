package com.smartinventorysystem.modules.purchase.entity;

import com.smartinventorysystem.modules.product.entity.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "Purchase_Details")
@Data
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PurchaseDetailID")
    private Integer purchaseDetailId;

    @ManyToOne
    @JoinColumn(name = "purchaseID")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
}