package com.tunehub.serviceimpl;

import com.tunehub.config.ImageStorageProperties;
import com.tunehub.entity.ImageMetadata;
import com.tunehub.repository.ImageMetadataRepository;
import com.tunehub.service.ImageService;
import com.tunehub.service.LocalImageStorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageStorageProperties properties;

    private final ImageMetadataRepository repository;

    private final LocalImageStorageService storageService;

    public ImageServiceImpl(ImageStorageProperties properties, ImageMetadataRepository repository, LocalImageStorageService storageService) {
        this.properties = properties;
        this.repository = repository;
        this.storageService = storageService;
    }

    @Override
    public ImageMetadata uploadImage(MultipartFile file, String ownerId) throws IOException {
        validateImage(file);
        String storedPath;
        try (InputStream inputStream = file.getInputStream()) {
            storedPath = storageService.storeFile(inputStream, file.getOriginalFilename());
        }
        ImageMetadata metadata = new ImageMetadata(
                UUID.randomUUID().toString(),
                file.getOriginalFilename(),
                storedPath,
                file.getContentType(),
                ownerId,
                file.getSize(),
                Instant.now()

        );
        return repository.save(metadata);
    }


    @Override
    public ImageMetadata getImageMetadata(String imageId) throws IOException {

        return repository.findById(imageId).orElseThrow(
                () -> new FileNotFoundException("File not found")
        );

    }

    @Override
    public Resource getImageResource(String imageId) throws IOException {
        ImageMetadata metadata = getImageMetadata(imageId);

        return storageService.getFileResource(metadata.getStoredFileName());
    }


    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String mimeType = file.getContentType();
        if (mimeType == null || !properties.allowedMimeTypes().contains(mimeType)) {
            throw new IllegalArgumentException("Unsupported file type: " + mimeType);
        }

    }
}
