package com.kodnest.projecttunehub.service;

import com.kodnest.projecttunehub.entity.Playlist;

import java.util.List;

/**
 * Service interface for managing Playlists.
 */
public interface PlaylistService {

   /**
    * Adds a playlist.
    *
    * @param playlist The playlist to add
    */
   public void addPlaylist(Playlist playlist);

  public List<Playlist> fetchAllPlaylists();
}