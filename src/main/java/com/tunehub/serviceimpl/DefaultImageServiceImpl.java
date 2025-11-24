package com.tunehub.serviceimpl;

import com.tunehub.entity.DefaultImage;
import com.tunehub.repository.DefaultImageRepository;
import com.tunehub.service.DefaultImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultImageServiceImpl implements DefaultImageService {

    private static final Logger log = LoggerFactory.getLogger(DefaultImageServiceImpl.class);

    private final DefaultImageRepository defaultImageRepository;
    private final List<String> avatarPaths;
    private final List<Long> defaultImageIds = new ArrayList<>(); // same order as avatarPaths

    public DefaultImageServiceImpl(DefaultImageRepository defaultImageRepository,
                                   @Value("${app.default-avatars.paths}") List<String> avatarPaths) {
        this.defaultImageRepository = defaultImageRepository;
        this.avatarPaths = avatarPaths;
    }

    /**
     * Ensure default images are present in DB. This will insert any default image (by sha256) that isn't already stored.
     * Populates defaultImageIds in the same order as avatarPaths.
     */
    @PostConstruct
    @Override
    public void ensureDefaultsPresent() {
        if (avatarPaths == null || avatarPaths.isEmpty()) {
            log.warn("No default avatar paths configured (app.default-avatars.paths).");
            return;
        }

        for (String path : avatarPaths) {
            try (InputStream is = new ClassPathResource(path).getInputStream()) {
                byte[] data = is.readAllBytes();
                String sha = sha256Hex(data);

                Optional<DefaultImage> existing = defaultImageRepository.findBySha256(sha);
                DefaultImage di;
                if (existing.isPresent()) {
                    di = existing.get();
                    log.debug("Default image already exists in DB: {} (sha {})", path, sha);
                } else {
                    di = new DefaultImage();
                    di.setName(Path.of(path).getFileName().toString());
                    di.setMimeType(detectMimeType(data).orElse("image/png"));
                    di.setSha256(sha);
                    di.setData(data);
                    di = defaultImageRepository.save(di);
                    log.info("Inserted default image into DB: {} -> id={}", path, di.getId());
                }
                defaultImageIds.add(di.getId());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load default avatar from classpath: " + path, e);
            }
        }

        if (defaultImageIds.isEmpty()) {
            log.warn("No default images were loaded into DB.");
        } else {
            log.info("Loaded {} default images (ids={}).", defaultImageIds.size(), defaultImageIds);
        }
    }

    /**
     * Deterministically pick a default-image id for the given email.
     * Throws IllegalStateException if defaults are not loaded.
     */
    @Override
    public Long pickDefaultImageIdForEmail(String email) {
        if (defaultImageIds.isEmpty()) {
            throw new IllegalStateException("No default avatars loaded");
        }
        String normalized = (email == null) ? "" : email.trim().toLowerCase();
        int idx = Math.floorMod(normalized.hashCode(), defaultImageIds.size());
        return defaultImageIds.get(idx);
    }

    @Override
    public Optional<DefaultImage> getDefaultImageById(Long id) {
        return defaultImageRepository.findById(id);
    }

    @Override
    public List<Long> listDefaultImageIds() {
        return Collections.unmodifiableList(defaultImageIds);
    }

    // ---------------- helpers ----------------

    private static String sha256Hex(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(data);
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to compute SHA-256", ex);
        }
    }

    private static Optional<String> detectMimeType(byte[] data) {
        try (InputStream is = new ByteArrayInputStream(data)) {
            String t = URLConnection.guessContentTypeFromStream(is);
            return Optional.ofNullable(t);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
