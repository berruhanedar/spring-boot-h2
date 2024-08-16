package com.berru.app.springbooth2.repository;

import com.berru.app.springbooth2.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {

    @EntityGraph(attributePaths = "genre") // Genre ilişkisinin otomatik olarak yüklenmesini sağlar
    @Query("SELECT m FROM Music m WHERE m.id = :id")
    Music findByIdWithGenre(@Param("id") int id);

    @EntityGraph(attributePaths = "genre")
    @Query("SELECT m FROM Music m")
    Page<Music> findAllWithGenre(Pageable pageable);

    @EntityGraph(attributePaths = "genre")
    @Query("SELECT m FROM Music m WHERE m.genre.id = :genreId")
    List<Music> findByGenreIdWithGenre(@Param("genreId") int genreId);
}
