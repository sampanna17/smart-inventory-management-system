package com.smartinventorysystem.modules.product.entity;

import com.smartinventorysystem.modules.category.entity.Category;
import com.smartinventorysystem.modules.unit.entity.Unit;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productID;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "unitID")
    private Unit unit;

    private String productName;
    private String description;

    private BigDecimal costPrice;
    private BigDecimal sellingPrice;

    private Integer stockQuantity;
    private Integer reorderLevel;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}