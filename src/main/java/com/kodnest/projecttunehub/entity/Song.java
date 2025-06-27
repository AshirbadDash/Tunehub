package com.kodnest.projecttunehub.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Represents a Song entity in the application.
 * This class is a JPA entity that maps to the Song table in the database.
 * Each instance of this class corresponds to a single row in the Song table.
 */
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // The unique identifier for the song

    private String name; // The name of the song
    private String artist; // The artist of the song
    private String genre; // The genre of the song
    private String link; // The link of the song

    @ManyToMany
    private List<Playlist> playlists; // The playlists that contain the song

    /**
     * Default constructor.
     * Initializes a new instance of the Song class.
     */
    public Song() {
        super();
    }

    /**
     * Parameterized constructor.
     * Initializes a new instance of the Song class with the given details.
     *
     * @param id        The unique identifier for the song
     * @param name      The name of the song
     * @param artist    The artist of the song
     * @param genre     The genre of the song
     * @param link      The link of the song
     * @param playlists The playlists that contain the song
     */
    public Song(int id, String name, String artist, String genre, String link, List<Playlist> playlists) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.link = link;
        this.playlists = playlists;
    }

    // Getters and setters for each field

    /**
     * Returns the unique identifier of the song.
     *
     * @return The unique identifier of the song
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the song.
     *
     * @param id The unique identifier of the song
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the song.
     *
     * @return The name of the song
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the song.
     *
     * @param name The name of the song
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the artist of the song.
     *
     * @return The artist of the song
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the artist of the song.
     *
     * @param artist The artist of the song
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Returns the genre of the song.
     *
     * @return The genre of the song
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the song.
     *
     * @param genre The genre of the song
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Returns the link of the song.
     *
     * @return The link of the song
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link of the song.
     *
     * @param link The link of the song
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Returns the playlists that contain the song.
     *
     * @return The playlists that contain the song
     */
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Sets the playlists that contain the song.
     *
     * @param playlists The playlists that contain the song
     */
    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    /**
     * Returns a string representation of the Song object.
     *
     * @return A string representation of the Song object
     */
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", link='" + link + '\'' +
                ", playlists=" + playlists +
                '}';
    }
}