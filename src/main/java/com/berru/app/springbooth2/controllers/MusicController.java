package com.berru.app.springbooth2.controllers;

import com.berru.app.springbooth2.entities.Music;
import com.berru.app.springbooth2.services.MusicService;
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
    public Music save(@RequestBody Music music) {
        return musicService.save(music);
    }

    @GetMapping
    public List<Music> list() {
        return musicService.list();
    }

    @GetMapping("/{id}")
    public Music getById(@PathVariable int id) {
        return musicService.getById(id);
    }

    @PutMapping("/{id}")
    public Music update(@PathVariable int id, @RequestBody Music music) {
        return musicService.update(id, music);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        musicService.delete(id);
    }

    @GetMapping("/by-genre/{genreId}")
    public List<Music> getByGenre(@PathVariable int genreId) {
        return musicService.getByGenre(genreId);
    }
}
