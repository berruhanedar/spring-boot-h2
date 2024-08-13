package com.berru.app.springbooth2.repository;

import com.berru.app.springbooth2.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {
    List<Music> findByGenreId(int genreId);
}

//findByGenreId(int genreId) metodu, belirli bir genreId'ye sahip olan Music nesnelerini döndürür.