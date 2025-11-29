package com.tunehub.repository;

import com.tunehub.model.entity.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, String> {

}
