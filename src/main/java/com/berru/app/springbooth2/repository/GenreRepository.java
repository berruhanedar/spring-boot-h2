package com.berru.app.springbooth2.repository;

import com.berru.app.springbooth2.entity.Genre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    @Query("SELECT g FROM Genre g LEFT JOIN FETCH g.musics")
    List<Genre> findAllWithMusics();
}

//JPQL/HQL kullanarak, ilişkili verileri tek bir sorguda çekebilirsiniz. join fetch kullanarak, ilişkili verileri birlikte alabilirsiniz.