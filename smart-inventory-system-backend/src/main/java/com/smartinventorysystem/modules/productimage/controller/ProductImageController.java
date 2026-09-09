package com.smartinventorysystem.modules.productimage.controller;

import com.smartinventorysystem.common.dto.ApiResponse;
import com.smartinventorysystem.constants.ApiRoutes;
import com.smartinventorysystem.modules.productimage.dto.response.ProductImageResponse;
import com.smartinventorysystem.modules.productimage.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(ApiRoutes.ProductImages.BASE)
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;
    private final Clock clock;

    @PostMapping(ApiRoutes.ProductImages.UPLOAD)
    public ResponseEntity<ApiResponse<ProductImageResponse>> upload(
            @PathVariable Integer productId,
            @RequestParam("file") MultipartFile file
    ){

        ProductImageResponse response =
                productImageService.uploadImage(
                        productId,
                        file
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<ProductImageResponse>builder()
                                .status(201)
                                .success(true)
                                .message("Image uploaded successfully")
                                .data(response)
                                .timestamp(LocalDateTime.now(clock))
                                .build()
                );
    }

    @GetMapping(ApiRoutes.ProductImages.GET_ALL)
    public ResponseEntity<List<ProductImageResponse>> getImages(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.ok(
                productImageService.getImages(productId)
        );
    }

    @DeleteMapping(ApiRoutes.ProductImages.DELETE)
    public ResponseEntity<ApiResponse<ProductImageResponse>> delete(@PathVariable Integer productId, @PathVariable Integer imageId){

        productImageService.deleteImage(productId, imageId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<ProductImageResponse>builder()
                                .status(HttpStatus.OK.value())
                                .success(true)
                                .message("Image Deleted successfully")
                                .timestamp(LocalDateTime.now(clock))
                                .build()
                );

    }
}