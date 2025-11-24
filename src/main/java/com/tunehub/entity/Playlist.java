//package com.kodnest.projecttunehub.entity;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
/// **
// * Represents a Playlist entity in the application.
// * This class is a JPA entity that maps to the Playlist table in the database.
// * Each instance of this class corresponds to a single row in the Playlist table.
// */
//@Entity
//public class Playlist {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id; // The unique identifier for the playlist
//
//    private String name; // The name of the playlist
//
//    @ManyToMany
//    private List<Song> songs; // The songs in the playlist
//
//    /**
//     * Default constructor.
//     * Initializes a new instance of the Playlist class.
//     */
//    public Playlist() {
//        super();
//    }
//
//    /**
//     * Parameterized constructor.
//     * Initializes a new instance of the Playlist class with the given details.
//     *
//     * @param id    The unique identifier for the playlist
//     * @param name  The name of the playlist
//     * @param songs The songs in the playlist
//     */
//    public Playlist(int id, String name, List<Song> songs) {
//        this.id = id;
//        this.name = name;
//        this.songs = songs;
//    }
//
//    // Getters and setters for each field
//
//    /**
//     * Returns the unique identifier of the playlist.
//     *
//     * @return The unique identifier of the playlist
//     */
//    public int getId() {
//        return id;
//    }
//
//    /**
//     * Sets the unique identifier of the playlist.
//     *
//     * @param id The unique identifier of the playlist
//     */
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    /**
//     * Returns the name of the playlist.
//     *
//     * @return The name of the playlist
//     */
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * Sets the name of the playlist.
//     *
//     * @param name The name of the playlist
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     * Returns the songs in the playlist.
//     *
//     * @return The songs in the playlist
//     */
//    public List<Song> getSongs() {
//        return songs;
//    }
//
//    /**
//     * Sets the songs in the playlist.
//     *
//     * @param songs The songs in the playlist
//     */
//    public void setSongs(List<Song> songs) {
//        this.songs = songs;
//    }
//
//    /**
//     * Returns a string representation of the Playlist object.
//     *
//     * @return A string representation of the Playlist object
//     */
//    @Override
//    public String toString() {
//        return "Playlist{" +
//                "id=" + id +
//                ", name='" + name + '\'' + '}';
//    }
//}