package com.smartinventorysystem.modules.productimage.mapper;

import com.smartinventorysystem.modules.productimage.dto.Response.ProductImageResponse;
import com.smartinventorysystem.modules.productimage.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(source = "product.productId", target = "productId")
    ProductImageResponse toResponse(ProductImage image);

    List<ProductImageResponse> toResponseList(
            List<ProductImage> images
    );
}