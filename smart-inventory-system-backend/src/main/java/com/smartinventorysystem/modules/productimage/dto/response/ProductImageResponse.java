package com.smartinventorysystem.modules.productimage.dto.response;

import lombok.Data;

@Data
public class ProductImageResponse {

    private Integer imageId;
    private Integer productId;
    private String imageURL;
    private String publicId;
}