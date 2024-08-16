package com.berru.app.springbooth2.controller;

import com.berru.app.springbooth2.dto.*;
import com.berru.app.springbooth2.service.MusicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musics")
public class MusicController {
    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @PostMapping
    public ResponseEntity<MusicDTO> save(@RequestBody @Valid NewMusicRequestDTO newMusicRequestDTO) {
        return musicService.save(newMusicRequestDTO);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse> getMusics(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(musicService.list(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getById(@PathVariable int id) {
        return musicService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteMusicResponseDTO> delete(@PathVariable int id) {
        return musicService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicDTO> update(@PathVariable int id, @Valid @RequestBody UpdateMusicRequestDTO updateMusicRequestDTO) {
        return musicService.update(id, updateMusicRequestDTO);
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<MusicDTO>> getByGenre(@PathVariable int genreId) {
        return musicService.getByGenre(genreId);
    }
}
