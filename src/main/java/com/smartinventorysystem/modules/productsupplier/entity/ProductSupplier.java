package com.smartinventorysystem.modules.productsupplier.entity;

import com.smartinventorysystem.modules.product.entity.Product;
import com.smartinventorysystem.modules.supplier.entity.Supplier;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_suppliers")
@Data
public class ProductSupplier {

    @EmbeddedId
    private ProductSupplierId id;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "ProductID")
    private Product product;

    @ManyToOne
    @MapsId("supplierID")
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;
}