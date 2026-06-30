package com.smartinventorysystem.modules.productimage.entity;

import com.smartinventorysystem.modules.product.entity.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Product_Images")
@Data
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageID;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;

    private String imageURL;
}
