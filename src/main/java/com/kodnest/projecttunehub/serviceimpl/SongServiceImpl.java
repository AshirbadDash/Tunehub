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
    SongRepository songRepository;

    /**
     * Adds a song to the database.
     *
     * @param song The song to add
     */
//    @Override
//    public void addSong(Song song) {
//
//        SongRepository.save(song);
//    }
    public void addSong(Song song) {
        if (song.getName() != null && song.getArtist() != null && song.getGenre() != null && song.getLink() != null) {
            songRepository.save(song);
        } else {
            System.out.println("Song contains null or empty values. Not adding to the database.");
        }
    }


    /**
     * Retrieves all songs from the database.
     *
     * @return A list of all songs
     */
    @Override
    public List<Song> viewSongs() {
        List<Song> songs = songRepository.findAll();
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
        Song song = songRepository.findByName(name);
        if (song == null) {
            return false;
        }
        return true;
    }

    @Override
    public void updateSong(Song song) {
        songRepository.save(song);
    }
}