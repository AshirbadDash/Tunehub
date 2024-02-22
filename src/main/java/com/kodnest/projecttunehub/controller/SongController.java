package com.kodnest.projecttunehub.controller;

import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SongController {

    @Autowired
    private SongService songService;


    @PostMapping("/viewSong")
    public String viewSong(@ModelAttribute Song song) {
        songService.viewSong(song);
        return "Song added";
    }

    @PostMapping("/addSong")
    public String addSong(@ModelAttribute Song song) {

        boolean songStatus = songService.songExists(song.getName());
        if (!songStatus) {
            songService.addSong(song);
            return "NewSong";
        } else {
            System.out.println("Song already exists with this name. Please try with another name.");

        }
        return "Admin";


//        return "redirect:/";

    }


}
