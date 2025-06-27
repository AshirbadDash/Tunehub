package com.kodnest.projecttunehub.repository;

import com.kodnest.projecttunehub.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Songs in the database.
 * This interface extends JpaRepository which provides JPA related methods for use with the Song entity.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    /**
     * Finds a song by its name.
     *
     * @param name The name of the song
     * @return The song with the given name
     */
    public Song findByName(String name);

    // Method to find a list of songs by name is commented out
    // List<Song> findByName(String name);
}