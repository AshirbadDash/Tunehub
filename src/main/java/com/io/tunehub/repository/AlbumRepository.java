package com.io.tunehub.repository;

import com.io.tunehub.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, String> {

//    Album findAlbumByName(String name);

}
