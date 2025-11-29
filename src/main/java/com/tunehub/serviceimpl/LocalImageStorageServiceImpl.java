package com.tunehub.serviceimpl;

import com.tunehub.config.ImageStorageProperties;
import com.tunehub.service.LocalImageStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.*;

@Service
public class LocalImageStorageServiceImpl implements LocalImageStorageService {

    private final ImageStorageProperties properties;
    private final Path rootPath;

    public LocalImageStorageServiceImpl(ImageStorageProperties properties) {
        this.properties = properties;
        this.rootPath = Paths.get(properties.basePath());
    }

    @Override
    public String storeFile(InputStream inputStream, String originalName) throws IOException {
        LocalDate today = LocalDate.now();
        // ./2025/03/25
        Path dateDirectory = rootPath
                .resolve(today.getYear() + File.separator +
                        String.format("%02d", today.getMonthValue()) + File.separator +
                        String.format("%02d", today.getDayOfMonth())
                );
        Files.createDirectories(dateDirectory);

        String extension = getFileExtension(originalName);
        String storedName = UUID.randomUUID() + "." + (extension.isEmpty() ? "" : "." + extension);
        Path filePath = dateDirectory.resolve(originalName);
        try (OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW)) {
            StreamUtils.copy(inputStream, outputStream);
        }
        return rootPath.relativize(filePath).toString();
    }

    @Override
    public Resource getFileResource(String storedPath) throws IOException {
        Path filePath = rootPath.resolve(storedPath).normalize().toAbsolutePath();
        Path normalizedRoot = rootPath.normalize().toAbsolutePath();
        if (!filePath.startsWith(normalizedRoot) || !Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            throw new IOException("File not found or access denied: " + storedPath);
        }
        return new UrlResource(filePath.toUri());
    }

    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return (lastDot == -1) ? "" : fileName.substring(lastDot + 1);
    }
}
