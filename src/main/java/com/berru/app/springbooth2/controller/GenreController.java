package com.berru.app.springbooth2.controller;

import com.berru.app.springbooth2.dto.*;
import com.berru.app.springbooth2.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<GenreDTO> save(@RequestBody @Valid NewGenreRequestDTO newGenreRequestDTO) {
        // MapStruct ile DTO'yu Entity'ye dönüştürme işlemi service katmanında yapılır
        return genreService.save(newGenreRequestDTO);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<GenreDTO>> list(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return genreService.listPaginated(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getById(@PathVariable int id) {
        // Service katmanında Genre entity'si DTO'ya dönüştürülerek döndürülür
        return genreService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> update(@PathVariable int id,
                                           @RequestBody @Valid UpdateGenreRequestDTO updateGenreRequestDTO) {
        // Güncelleme işlemi sonrası, MapStruct ile entity DTO'ya dönüştürülür ve döndürülür
        return genreService.update(id, updateGenreRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteGenreRequestDTO> delete(@PathVariable int id) {
        // Silme işlemi sonrası, DeleteResponseDTO döndürülür
        return genreService.delete(id);
    }
}
