package com.kodnest.projecttunehub.serviceimpl;

import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.repository.SongRepository;
import com.kodnest.projecttunehub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    SongRepository SongRepository;

//    @Override
//    public String addSong(Song song) {
//        SongRepository.save(song);
//        return "Song added successfully";
//    }

    @Override
    public String addSong(Song song) {
        // Check if the song already exists in the database by name
        List<Song> existingSongs = SongRepository.findByName(song.getName());

        // If the song already exists, return a message
        if (!existingSongs.isEmpty()) {
            return "Song already exists";
        }

        // If the song does not exist, save it and return a success message
        SongRepository.save(song);
        return "Song added successfully!";
    }
}

//
//    @Override
//    public String addSong(Song song) {
//        SongRepository.save(song);
//        return "Song added successfully";
//}



