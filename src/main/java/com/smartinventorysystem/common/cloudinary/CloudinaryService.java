package com.smartinventorysystem.common.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map<String, Object> uploadImage(MultipartFile file) {

        try {

            Map<?, ?> uploadResult =
                    cloudinary.uploader()
                            .upload(
                                    file.getBytes(),
                                    ObjectUtils.emptyMap()
                            );


            return Map.of(
                    "secure_url",
                    uploadResult.get("secure_url"),

                    "public_id",
                    uploadResult.get("public_id")
            );


        } catch (IOException e) {

            throw new RuntimeException("Image upload failed");
        }
    }

    public void deleteImage(String publicId) {

        try {

            cloudinary.uploader()
                    .destroy(
                            publicId,
                            ObjectUtils.emptyMap()
                    );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to delete image from Cloudinary"
            );
        }
    }
}