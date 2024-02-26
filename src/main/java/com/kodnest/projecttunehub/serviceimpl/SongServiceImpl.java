package com.kodnest.projecttunehub.serviceimpl;

import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.repository.SongRepository;
import com.kodnest.projecttunehub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing Songs.
 * This class implements the SongService interface.
 */
@Service
public class SongServiceImpl implements SongService {

    /**
     * The SongRepository that this service will use to interact with the database.
     */
    @Autowired
    SongRepository SongRepository;

    /**
     * Adds a song to the database.
     *
     * @param song The song to add
     * @return A string indicating the status of the operation
     */
    @Override
    public String addSong(Song song) {
        SongRepository.save(song);
        return "Song added successfully";
    }

    /**
     * Retrieves all songs from the database.
     *
     * @return A list of all songs
     */
    @Override
    public List<Song> viewSongs() {
        List<Song> songs = SongRepository.findAll();
        return songs;
    }

    /**
     * Checks if a song exists by its name in the database.
     *
     * @param name The name of the song
     * @return A boolean indicating whether the song exists
     */
    @Override
    public boolean songExists(String name) {
        Song song = SongRepository.findByName(name);
        if (song == null) {
            return false;
        }
        return true;
    }
}