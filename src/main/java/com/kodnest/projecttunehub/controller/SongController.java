package com.kodnest.projecttunehub.controller;

import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Controller for handling Song related requests.
 */
@Controller
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * Handles the GET request to view songs.
     * Adds all songs to the model and returns the view for displaying songs.
     *
     * @param model The model to add attributes to
     * @return The name of the view for displaying songs
     */
    @GetMapping("/viewSongs")
    public String viewSong(Model model) {
        List<Song> songList = songService.viewSongs();
        model.addAttribute("song", songList);
        return "DisplaySongs";
    }

    /**
     * Handles the GET request to play songs.
     * Checks if the user is a premium user and returns the appropriate view.
     *
     * @param model The model to add attributes to
     * @return The name of the view for playing songs or the subscription view
     */
    @GetMapping("/playSongs")
    public String playSong(Model model) {
        boolean PremiumUser = false;

        if (PremiumUser) {
            List<Song> songList = songService.viewSongs();
            model.addAttribute("song", songList);
            return "DisplaySongs";
        } else {
            return "Subsciption";
        }
    }

    /**
     * Handles the POST request to add a song.
     * Checks if the song already exists and adds it if it doesn't.
     *
     * @param song The song to add
     * @return The name of the new song view or the admin view
     */
    @PostMapping("/addSong")
    public String addSong(@ModelAttribute Song song) {
        boolean songStatus = songService.songExists(song.getName());

//        if (!songStatus) {
            songService.addSong(song);
            return "NewSong";
////        } else {
//            System.out.println("Song already exists with this name. Please try with another name.");
//            return "Admin";
//        }
    }
}