package com.berru.app.springbooth2.controllers;

import com.berru.app.springbooth2.entities.Music;
import com.berru.app.springbooth2.services.MusicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //Buranın bir controller sınıfı olduğunu belirtmek için kullanıyoruz
@RequestMapping("/musics")
public class MusicController {
    final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @PostMapping
    public ResponseEntity<Music> save(@RequestBody Music music) {
        return musicService.save(music);
    }

    @GetMapping
    public ResponseEntity<List<Music>> list() {
        return musicService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Music> getById(@PathVariable int id) {
        return musicService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Music> update(@PathVariable int id, @RequestBody Music music) {
        return musicService.update(id, music);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return musicService.delete(id);
    }

    @GetMapping("/by-genre/{genreId}")
    public ResponseEntity<List<Music>> getByGenre(@PathVariable int genreId) {
        return musicService.getByGenre(genreId);
    }
}