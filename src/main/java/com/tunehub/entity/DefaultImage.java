package com.tunehub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "default_images",
        uniqueConstraints = @UniqueConstraint(columnNames = "sha256"))
public class DefaultImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "mime_type", nullable = false, length = 64)
    private String mimeType;

    @Column(nullable = false, length = 64, unique = true)
    private String sha256;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] data;
    // --- constructors (optional) ---
    public DefaultImage() {}

    // --- getters / setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public String getSha256() { return sha256; }
    public void setSha256(String sha256) { this.sha256 = sha256; }

    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
}
