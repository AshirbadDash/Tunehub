package com.io.tunehub.service;

import com.io.tunehub.entity.DefaultImage;

import java.util.List;
import java.util.Optional;

public interface DefaultImageService {

    /**
     * Deterministically pick a default-image id for the given email.
     * Throws IllegalStateException if defaults are not loaded.
     */
    Long pickDefaultImageIdForEmail(String email);

    /**
     * Return the DefaultImage entity for the given id.
     * Used by controllers to serve the canonical blob.
     */
    Optional<DefaultImage> getDefaultImageById(Long id);

    /**
     * Return the list of loaded default-image ids in the configured order.
     * Useful for admin or testing.
     */
    List<Long> listDefaultImageIds();

    /**
     * Ensure defaults are present in DB. Implementation may be annotated with @PostConstruct
     * and also expose this method so tests or an admin trigger can call it explicitly.
     */
    void ensureDefaultsPresent();
}
