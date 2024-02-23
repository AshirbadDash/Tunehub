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

@Controller
public class SongController {

    @Autowired
    private SongService songService;


    @GetMapping ("/viewSongs")
    public String viewSong( Model model) {
        List <Song> songList= songService.viewSongs();
        model.addAttribute("song", songList);

        System.out.println(songList);
        return "DisplaySongs";
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
