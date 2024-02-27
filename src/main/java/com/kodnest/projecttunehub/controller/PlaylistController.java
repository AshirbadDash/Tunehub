package com.kodnest.projecttunehub.controller;

import com.kodnest.projecttunehub.entity.Playlist;
import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.service.PlaylistService;
import com.kodnest.projecttunehub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Controller for handling Playlist related requests.
 */
@Controller
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private SongService songService;

    /**
     * Handles the GET request to create playlists.
     * Adds all songs to the model and returns the view for creating playlists.
     *
     * @param model The model to add attributes to
     * @return The name of the view for creating playlists
     */
    @GetMapping("/createPlaylists")
    public String createPlaylists(Model model) {
        List<Song> songList = songService.viewSongs();
        model.addAttribute("song", songList);
        return "createPlaylists";
    }

    /**
     * Handles the POST request to add playlists.
     * Adds the playlist to the database and updates the songs in the playlist.
     *
     * @param playlist The playlist to add
     * @return The name of the admin view
     */
    @PostMapping("/addPlaylists")
    public String addPlaylists(@ModelAttribute Playlist playlist) {
        //updating the playlist table
        playlistService.addPlaylist(playlist);

        // Updating the song table
        List<Song> songList = playlist.getSongs();
        for (Song song : songList) {
            song.getPlaylists().add(playlist);
            songService.updateSong(song);
        }
        return "Admin";
    }

//    @GetMapping("/viewPlaylists")
//    public String viewSong(Model model) {
//        List<Song> songList = songService.viewSongs();
//        model.addAttribute("song", songList);
//        return "ViewPlaylist";
//    }


    @GetMapping("/viewPlaylists")
    public String viewPlaylists(Model model) {
        List<Playlist> allPlaylist = playlistService.fetchAllPlaylists();
        model.addAttribute("allPlaylist", allPlaylist);
        return "ViewPlaylist";
    }
}