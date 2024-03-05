package com.kodnest.projecttunehub.serviceimpl;

import com.kodnest.projecttunehub.entity.Playlist;
import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.repository.PlaylistRepository;
import com.kodnest.projecttunehub.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing Playlists.
 * This class implements the PlaylistService interface.
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {

    /**
     * The PlaylistRepository that this service will use to interact with the database.
     */
    @Autowired
    private PlaylistRepository playlistRepository;

    /**
     * Adds a playlist to the database.
     *
     * @param playlist The playlist to add
     */
    @Override
    public void addPlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public List<Playlist> fetchAllPlaylists() {
        List<Playlist> allPlaylist = playlistRepository.findAll();
        return allPlaylist;
    }

    @Override
    public boolean updatePlaylist(Integer playlistId, Song song) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if (playlist.isPresent()) {
            playlist.get().getSongs().add(song);
            playlistRepository.save(playlist.get());
            return true;
        }
        return false;


    }


}