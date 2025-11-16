//package com.kodnest.projecttunehub.controller;
//
//import com.kodnest.projecttunehub.entity.Playlist;
//import com.kodnest.projecttunehub.entity.Song;
//import com.kodnest.projecttunehub.service.PlaylistService;
//import com.kodnest.projecttunehub.service.MusicService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
/// **
// * Controller for handling Playlist related requests.
// */
//@Controller
//public class PlaylistController {
//
//    @Autowired
//    private PlaylistService playlistService;
//
//    @Autowired
//    private MusicService musicService;
//
//    /**
//     * Handles the GET request to create playlists. Adds all songs to the model and
//     * returns the view for creating playlists.
//     *
//     * @param model The model to add attributes to
//     * @return The name of the view for creating playlists
//     */
//    @GetMapping("/createPlaylists")
//    public String createPlaylists(Model model) {
//        List<Song> songList = musicService.viewSongs();
//        model.addAttribute("song", songList);
//        return "createPlaylists";
//    }
//
//    /**
//     * Handles the POST request to add playlists. Adds the playlist to the database
//     * and updates the songs in the playlist.
//     *
//     * @param playlist The playlist to add
//     * @return The name of the admin view
//     */
//    @PostMapping("/addPlaylists")
//    public String addPlaylists(@ModelAttribute Playlist playlist, Model model) {
//        // updating the playlist table
//        Playlist existingPlaylist = playlistService.findByName(playlist.getName());
//
//        if (existingPlaylist != null) {
//            model.addAttribute("error", "A playlist with this name already exists!");
//            return "createPlaylists"; // Show the same page with an error message
//        }
//        playlistService.addPlaylist(playlist);
//
//        // Updating the song table
//        List<Song> songList = playlist.getSongs();
//        for (Song song : songList) {
//            song.getPlaylists().add(playlist);
//            musicService.updateSong(song);
//        }
//        return "Admin";
//    }
//
//    @GetMapping("/viewPlaylists")
//    public String viewPlaylists(Model model) {
//        List<Playlist> allPlaylist = playlistService.fetchAllPlaylists();
//        model.addAttribute("allPlaylist", allPlaylist);
//        return "ViewPlaylist";
//    }
//
//    @PostMapping("/updatePlaylist")
//    public String updatePlaylist(@RequestParam(value = "id", required = false) Integer playlistId,
//                                 @RequestParam(value = "songId", required = false) Integer songId,
//                                 Model model) {
//
//        if (playlistId == null) {
//            model.addAttribute("error", "Missing required parameter 'id'");
//            return "updatePlaylist";
//        }
//        if (songId == null) {
//            model.addAttribute("error", "Missing required parameter 'songId'");
//            return "updatePlaylist";
//        }
//
//        Song song = musicService.getSongById(songId);
//        if (song == null) {
//            model.addAttribute("error", "Error: Song not found");
//            return "updatePlaylist";
//        }
//
//        boolean isUpdated = playlistService.updatePlaylist(playlistId, song);
//        model.addAttribute("updateStatus", isUpdated ? "Playlist updated successfully" : "Failed to update playlist");
//
//        return "updatePlaylist";  // Ensure updatePlaylist.html exists in templates
//    }
//}