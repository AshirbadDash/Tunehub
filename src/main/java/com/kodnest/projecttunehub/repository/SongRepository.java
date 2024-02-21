package com.kodnest.projecttunehub.repository;

import com.kodnest.projecttunehub.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {


    List<Song> findByName(String name);
}
