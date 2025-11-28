package com.bloomwave.repository;

import com.bloomwave.entity.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, String> {

}
