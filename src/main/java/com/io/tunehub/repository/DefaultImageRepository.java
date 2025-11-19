package com.io.tunehub.repository;

import com.io.tunehub.entity.DefaultImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefaultImageRepository extends JpaRepository<DefaultImage, Long> {
    Optional<DefaultImage> findBySha256(String sha256);
}
