package com.kodnest.projecttunehub.serviceimpl;

import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.repository.SongRepository;
import com.kodnest.projecttunehub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    SongRepository SongRepository;

    @Override
    public String addSong(Song song) {
        SongRepository.save(song);
        return "Song added successfully";
    }


}
