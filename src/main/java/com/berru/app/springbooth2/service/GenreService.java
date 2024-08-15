package com.berru.app.springbooth2.service;

import com.berru.app.springbooth2.dto.DeleteGenreRequestDTO;
import com.berru.app.springbooth2.dto.GenreDTO;
import com.berru.app.springbooth2.dto.NewGenreRequestDTO;
import com.berru.app.springbooth2.dto.UpdateGenreRequestDTO;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.repository.GenreRepository;
import com.berru.app.springbooth2.mapper.GenreMapper; // MapStruct Mapper sınıfını dahil ettik
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import com.berru.app.springbooth2.exception.NotFoundException;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper; // GenreMapper bean'ini ekledik

    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    public ResponseEntity<GenreDTO> save(NewGenreRequestDTO newGenreRequestDTO) {
        Genre genre = genreMapper.toEntity(newGenreRequestDTO); // MapStruct ile DTO'dan Entity'ye dönüşüm
        Genre savedGenre = genreRepository.save(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(genreMapper.toDto(savedGenre)); // Entity'den DTO'ya dönüşüm
    }

    public ResponseEntity<List<GenreDTO>> list() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreDTO> genreDTOs = genres.stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(genreDTOs);
    }


    public ResponseEntity<GenreDTO> getById(int id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found"));
        return ResponseEntity.ok(genreMapper.toDto(genre));
    }

    public ResponseEntity<GenreDTO> update(int id, UpdateGenreRequestDTO updateGenreRequestDTO) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        genreMapper.updateGenreFromDto(updateGenreRequestDTO, genre); // DTO'dan Entity'ye güncelleme
        Genre updatedGenre = genreRepository.save(genre);

        return ResponseEntity.ok(genreMapper.toDto(updatedGenre));
    }

    public ResponseEntity<DeleteGenreRequestDTO> delete(int id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            DeleteGenreRequestDTO response = new DeleteGenreRequestDTO(id, "Genre deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        throw new NotFoundException("Genre not found");
    }

}
