package com.berru.app.springbooth2.repository;

import com.berru.app.springbooth2.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}

