package com.smartinventorysystem.modules.productimage.service;

import com.smartinventorysystem.common.service.CloudinaryService;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.modules.product.entity.Product;
import com.smartinventorysystem.modules.product.repository.ProductRepository;
import com.smartinventorysystem.modules.productimage.dto.Response.ProductImageResponse;
import com.smartinventorysystem.modules.productimage.entity.ProductImage;
import com.smartinventorysystem.modules.productimage.mapper.ProductImageMapper;
import com.smartinventorysystem.modules.productimage.repository.ProductImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductImageMapper mapper;

    @Override
    public ProductImageResponse uploadImage(
            Integer productId,
            MultipartFile file
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Product not found"
                        )
                );

        Map<String, Object> uploadResult =
                cloudinaryService.uploadImage(file);

        ProductImage image = new ProductImage();

        image.setProduct(product);
        image.setImageURL(
                uploadResult.get("secure_url").toString()
        );
        image.setPublicId(
                uploadResult.get("public_id").toString()
        );

        return mapper.toResponse(
                imageRepository.save(image)
        );

    }

    @Override
    public List<ProductImageResponse> getImages(
            Integer productId
    ) {

        if(!productRepository.existsById(productId)){

            throw new ResourceNotFoundException(
                    "Product not found"
            );
        }
        return mapper.toResponseList(
                imageRepository.findByProductProductId(productId)
        );
    }

    @Transactional
    @Override
    public void deleteImage(Integer productId, Integer imageId) {

        ProductImage image =
                imageRepository.findById(imageId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Image not found"
                                )
                        );

        if(!image.getProduct()
                .getProductId()
                .equals(productId)){

            throw new RuntimeException(
                    "Image does not belong to product"
            );
        }

        // Delete from Cloudinary
        cloudinaryService.deleteImage(
                image.getPublicId()
        );

        imageRepository.delete(image);

    }
}