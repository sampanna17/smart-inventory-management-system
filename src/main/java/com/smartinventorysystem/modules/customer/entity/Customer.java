package com.smartinventorysystem.modules.customer.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerID;

    private String customerName;
    private String phone;
    private String email;
    private String address;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
