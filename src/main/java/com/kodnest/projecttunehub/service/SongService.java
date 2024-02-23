package com.kodnest.projecttunehub.service;

import com.kodnest.projecttunehub.entity.Song;

import java.util.List;


public interface SongService {
    public  String addSong(Song song);



   public  List<Song> viewSongs();


   public  boolean songExists(String name);


}
