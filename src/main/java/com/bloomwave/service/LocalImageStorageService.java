package com.bloomwave.service;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public interface LocalImageStorageService {
    public String storeFile(InputStream inputStream, String originalName) throws IOException;

    public Resource getFileResource(String storedPath) throws IOException;
}
