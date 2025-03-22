package com.kodnest.projecttunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kodnest.projecttunehub.entity.Playlist;
import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.service.PlaylistService;
import com.kodnest.projecttunehub.service.SongService;

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
	 * Handles the GET request to create playlists. Adds all songs to the model and
	 * returns the view for creating playlists.
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
	 * Handles the POST request to add playlists. Adds the playlist to the database
	 * and updates the songs in the playlist.
	 *
	 * @param playlist The playlist to add
	 * @return The name of the admin view
	 */
	@PostMapping("/addPlaylists")
	public String addPlaylists(@ModelAttribute Playlist playlist) {
		// updating the playlist table
		playlistService.addPlaylist(playlist);

		// Updating the song table
		List<Song> songList = playlist.getSongs();
		for (Song song : songList) {
			song.getPlaylists().add(playlist);
			songService.updateSong(song);
		}
		return "Admin";
	}

	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model) {
		List<Playlist> allPlaylist = playlistService.fetchAllPlaylists();
		model.addAttribute("allPlaylist", allPlaylist);
		return "ViewPlaylist";
	}

	@GetMapping("/updatePlaylist")
	public String updatePlaylist(@RequestParam(value = "id", required = false) Integer playlistId,
								 @RequestParam(value = "songId", required = false) Integer songId,
								 Model model) {

		if (playlistId == null) {
			model.addAttribute("error", "Missing required parameter 'id'");
			return "errorPage";  // Create an errorPage.html in templates folder
		}
		if (songId == null) {
			model.addAttribute("error", "Missing required parameter 'songId'");
			return "errorPage";
		}

		Song song = songService.getSongById(songId);
		if (song == null) {
			model.addAttribute("error", "Error: Song not found");
			return "errorPage";
		}

		boolean isUpdated = playlistService.updatePlaylist(playlistId, song);
		model.addAttribute("updateStatus", isUpdated ? "Playlist updated successfully" : "Failed to update playlist");

		return "updatePlaylist";  // Ensure updatePlaylist.html exists in templates
	}
}