package com.smartinventorysystem.modules.supplier.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Suppliers")
@Data
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierID;

    private String supplierName;
    private String phone;
    private String email;
    private String address;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}