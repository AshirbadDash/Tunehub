package com.kodnest.projecttunehub.service;

import com.kodnest.projecttunehub.entity.Song;

import java.util.List;

/**
 * Service interface for managing Songs.
 */
public interface SongService {

    /**
     * Adds a song.
     *
     * @param song The song to add
     */
    public void addSong(Song song);

    /**
     * Retrieves all songs.
     *
     * @return A list of all songs
     */
    public List<Song> viewSongs();

    /**
     * Checks if a song exists by its name.
     *
     * @param name The name of the song
     * @return A boolean indicating whether the song exists
     */
    public boolean songExists(String name);

    public void updateSong(Song song);

    Song getSongById(Integer songId);
}