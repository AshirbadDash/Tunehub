package com.kodnest.projecttunehub.controller;

import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping  ("/addSong")
    public String addSong(@ModelAttribute Song song) {
        songService.addSong(song);
//        return "redirect:/";
        return "NewSong";
    }


}
