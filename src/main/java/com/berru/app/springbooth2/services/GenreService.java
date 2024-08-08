package com.berru.app.springbooth2.services;

import com.berru.app.springbooth2.entities.Genre;
import com.berru.app.springbooth2.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenreService {
    final GenreRepository genreRepository; // bir kere değer atayacağız ve değimeyecek bir daha

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    public List<Genre> list() {
        return genreRepository.findAll();
    }

    public Genre getById(int id) {
        return genreRepository.findById(id).orElse(null);
    }

    public Genre update(int id, Genre genre) {
        if (genreRepository.existsById(id)) {
            genre.setId(id);
            return genreRepository.save(genre);
        }
        return null;
    }

    public void delete(int id) {
        genreRepository.deleteById(id);
    }

}
