package com.tunehub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

@ConfigurationProperties(prefix = "app.image-storage")
@Component
public record ImageStorageProperties(
        String basePath,
        Set<String> allowedMimeTypes
) {
    public ImageStorageProperties() {
        this(
                "./images",
                Set.of(
                        "image/jpeg",
                        "image/png",
                        "image/gif",
                        "image/webp"
                )
        );
    }
}
