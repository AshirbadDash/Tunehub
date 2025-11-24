package com.tunehub.serviceimpl;

import com.tunehub.entity.Music;
import com.tunehub.service.MusicService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MusicServiceImpl implements MusicService {

    @Override
    public Music addMusic(String title, String artistName, MultipartFile audioFile) {
        Music musicFile=new Music();
        musicFile.setTitle(title);
//        musicFile.setArtist();

        return null;
    }
}