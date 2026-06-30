package com.smartinventorysystem.modules.category.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryID;

    private String categoryName;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
