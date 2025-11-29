package com.tunehub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image_metadata")
@ToString
@EqualsAndHashCode
public class ImageMetadata {
    @Id
    private String id;
    private String originalFileName;
    private String storedFileName;
    private String mimeType;
    private String ownerId;
    private long size;
    private Instant createdAt;
}
