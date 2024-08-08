package com.berru.app.springbooth2.services;

import com.berru.app.springbooth2.entities.Genre;
import com.berru.app.springbooth2.entities.Music;
import com.berru.app.springbooth2.repositories.GenreRepository;
import com.berru.app.springbooth2.repositories.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    final MusicRepository musicRepository;
    final GenreRepository genreRepository;

    public MusicService(MusicRepository musicRepository, GenreRepository genreRepository) {
        this.musicRepository = musicRepository;
        this.genreRepository = genreRepository;
    }

    public Music save(Music music) {
        Genre genre = genreRepository.findById(music.getGenre().getId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        music.setGenre(genre);
        return musicRepository.save(music);
    }

    public List<Music> list() {
        return musicRepository.findAll();
    }

    public Music getById(int id) {
        return musicRepository.findById(id).orElseThrow(() -> new RuntimeException("Music not found"));
    }

    public Music update(int id, Music music) {
        Music existingMusic = musicRepository.findById(id).orElseThrow(() -> new RuntimeException("Music not found"));
        existingMusic.setName(music.getName());
        Genre genre = genreRepository.findById(music.getGenre().getId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        existingMusic.setGenre(genre);
        return musicRepository.save(existingMusic);
    }

    public void delete(int id) {
        musicRepository.deleteById(id);
    }

    public List<Music> getByGenre(int genreId) {
        return musicRepository.findByGenreId(genreId);
    }
}
