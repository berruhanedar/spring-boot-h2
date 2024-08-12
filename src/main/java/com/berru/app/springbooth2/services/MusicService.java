package com.berru.app.springbooth2.services;

import com.berru.app.springbooth2.entities.Genre;
import com.berru.app.springbooth2.entities.Music;
import com.berru.app.springbooth2.repositories.GenreRepository;
import com.berru.app.springbooth2.repositories.MusicRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class MusicService {
    final MusicRepository musicRepository;
    final GenreRepository genreRepository;

    public MusicService(MusicRepository musicRepository, GenreRepository genreRepository) {
        this.musicRepository = musicRepository;
        this.genreRepository = genreRepository;
    }

    public ResponseEntity<Music> save(Music music) {
        // Verilen Genre ID'sine sahip bir Genre nesnesi arar. Eğer bulunamazsa, bir hata fırlatılır.
        Genre genre = genreRepository.findById(music.getGenre().getId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        // Bulunan Genre nesnesini müzik nesnesine atar.
        music.setGenre(genre);

        // Müzik adı yalnızca harfler ve boşluklar içermiyorsa bir hata fırlatılır.
        if (!music.getName().matches("^[a-zA-Z ]+$")) {
            throw new IllegalArgumentException("Music name can only contain letters and spaces.");
        }

        // Müzik nesnesini veritabanına kaydeder ve HTTP 201 (Created) yanıtı döner.
        Music savedMusic = musicRepository.save(music);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMusic);
    }


    public ResponseEntity<List<Music>> list() {
        List<Music> musics = musicRepository.findAll();
        return ResponseEntity.ok(musics);
    }

    public ResponseEntity<Music> getById(int id) {
        return musicRepository.findById(id)
                .map(music -> ResponseEntity.ok(music))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<Music> update(int id, Music music) {
        Music existingMusic = musicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Music not found"));
        existingMusic.setName(music.getName());
        Genre genre = genreRepository.findById(music.getGenre().getId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        existingMusic.setGenre(genre);
        Music updatedMusic = musicRepository.save(existingMusic);
        return ResponseEntity.ok(updatedMusic);
    }

    public ResponseEntity<Void> delete(int id) {
        if (musicRepository.existsById(id)) {
            musicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<List<Music>> getByGenre(int genreId) {
        List<Music> musics = musicRepository.findByGenreId(genreId);
        return ResponseEntity.ok(musics);
    }
}