package com.tunehub.service;

import com.tunehub.entity.Music;
import org.springframework.web.multipart.MultipartFile;

public interface MusicService {
Music addMusic(String title, String genre, MultipartFile audioFile);

}