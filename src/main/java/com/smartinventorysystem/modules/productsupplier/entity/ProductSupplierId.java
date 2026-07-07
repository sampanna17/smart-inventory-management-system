package com.smartinventorysystem.modules.productsupplier.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ProductSupplierId implements Serializable {

    private Integer productID;
    private Integer supplierID;
}