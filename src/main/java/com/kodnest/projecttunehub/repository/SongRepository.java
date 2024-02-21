package com.kodnest.projecttunehub.repository;

import com.kodnest.projecttunehub.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {



}
