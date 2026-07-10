package com.smartinventorysystem.modules.productimage.service;

import com.smartinventorysystem.modules.productimage.dto.response.ProductImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {

    ProductImageResponse uploadImage(Integer productId, MultipartFile file);

    List<ProductImageResponse> getImages(Integer productId);

    void deleteImage(Integer productId, Integer imageId);
}