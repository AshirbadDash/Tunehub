package com.io.tunehub.entity;

import jakarta.persistence.*;

import java.util.*;


@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String genre;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Music> music = new ArrayList<>();

    public Artist() {
    }

    public Artist(User user, String genre, List<Album> albums, List<Music> music) {

        this.user = user;
        this.genre = genre;
        this.albums = albums;
        this.music = music;
    }

    @Override
    public String toString() {
        return "Artist{" + "user=" + user + ", genre='" + genre + '\'' + ", albums=" + albums + ", music=" + music + '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Music> getMusic() {
        return music;
    }

    public void setMusic(List<Music> music) {
        this.music = music;
    }
}
