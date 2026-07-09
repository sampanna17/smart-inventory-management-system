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
    @Column(name = "ImageID")
    private Integer imageID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productID", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String imageURL;
}
