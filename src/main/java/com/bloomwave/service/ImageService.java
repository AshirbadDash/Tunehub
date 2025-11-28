package com.bloomwave.service;

import com.bloomwave.entity.ImageMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public ImageMetadata uploadImage(MultipartFile file, String ownerId) throws IOException;

    public ImageMetadata getImageMetadata(String imageId) throws IOException;

    public Resource getImageResource(String imageId) throws IOException;
}
