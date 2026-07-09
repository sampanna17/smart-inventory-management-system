package com.smartinventorysystem.modules.productimage.dto.Response;

import lombok.Data;

@Data
public class ProductImageResponse {

    private Integer imageId;
    private Integer productId;
    private String imageURL;
}