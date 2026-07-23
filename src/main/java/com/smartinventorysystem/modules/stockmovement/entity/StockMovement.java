package com.smartinventorysystem.modules.stockmovement.entity;

import com.smartinventorysystem.enums.MovementType;
import com.smartinventorysystem.modules.product.entity.Product;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Stock_Movements")
@Data
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movementID;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;

    private Integer userID;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    private Integer quantity;

    private LocalDateTime movementDate;

    private String remarks;
}