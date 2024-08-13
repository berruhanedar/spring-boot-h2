package com.berru.app.springbooth2.controllers;

import com.berru.app.springbooth2.dto.MusicDTO;
import com.berru.app.springbooth2.dto.NewMusicRequestDTO;
import com.berru.app.springbooth2.dto.UpdateMusicRequestDTO;
import com.berru.app.springbooth2.entity.Music;
import com.berru.app.springbooth2.service.MusicService;
import jakarta.validation.Valid;
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

    // Müzik ekleme işlemi
    @PostMapping
    public ResponseEntity<MusicDTO> save(@RequestBody @Valid NewMusicRequestDTO newMusicRequestDTO) {
        return musicService.save(newMusicRequestDTO);
    }

    @GetMapping
    public ResponseEntity<List<MusicDTO>> list() {
        return musicService.list();
    }

    // Belirli bir müziği ID'ye göre getir
    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getById(@PathVariable int id) {
        return musicService.getById(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return musicService.delete(id);
    }

    // Belirli bir genre'e ait müzikleri getir
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<MusicDTO>> getByGenre(@PathVariable int genreId) {
        return musicService.getByGenre(genreId);
    }
}