package com.berru.app.springbooth2.repositories;

import com.berru.app.springbooth2.entities.Music;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {
    List<Music> findByGenreId(int genreId);
}

//findByGenreId(int genreId) metodu, belirli bir genreId'ye sahip olan Music nesnelerini döndürür.