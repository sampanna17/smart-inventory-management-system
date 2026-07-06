package com.smartinventorysystem.modules.unit.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Units")
@Data
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UnitID")
    private Integer unitId;

    @Column(name = "UnitName", nullable = false, unique = true, length = 50)
    private String unitName;
}
