package com.tunehub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private User uploader;

    // ---------------- Constructors ---------------- //

    public Music() {
    }

    public Music(String title, String description, Artist artist, Album album, User uploader) {
        this.title = title;
        this.description = description;
        this.artist = artist;
        this.album = album;
        this.uploader = uploader;
    }

    // ---------------- Safe toString() ---------------- //

    @Override
    public String toString() {
        return "Music{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", artistId=" + (artist != null ? artist.getId() : null) +
                ", albumId=" + (album != null ? album.getId() : null) +
                ", uploaderId=" + (uploader != null ? uploader.getId() : null) +
                '}';
    }

    // ---------------- Getters & Setters ---------------- //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }
}
