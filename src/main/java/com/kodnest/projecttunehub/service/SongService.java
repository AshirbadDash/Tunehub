package com.kodnest.projecttunehub.service;

import com.kodnest.projecttunehub.entity.Song;

import java.util.List;


public interface SongService {
    public  String addSong(Song song);



   public  List<Song> viewSong(Song song);

   public  boolean songExists(String name);

//    boolean songExists(String name);
}
