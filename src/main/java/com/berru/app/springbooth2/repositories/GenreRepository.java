package com.berru.app.springbooth2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

//interface olma sebebi bir method içerisini doldurmadan ilerleyeceğiz
public interface GenreRepository extends JpaRepository<Genre,Integer > {
}
