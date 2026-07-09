package com.smartinventorysystem.modules.productimage.service;

import com.smartinventorysystem.modules.productimage.dto.Response.ProductImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {

    ProductImageResponse uploadImage(Integer productId, MultipartFile file);

    List<ProductImageResponse> getImages(Integer productId);

    void deleteImage(Integer productId, Integer imageId);
}