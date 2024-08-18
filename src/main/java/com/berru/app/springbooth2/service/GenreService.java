package com.berru.app.springbooth2.service;

import com.berru.app.springbooth2.dto.*;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.mapper.GenreMapper;
import com.berru.app.springbooth2.repository.GenreRepository;
import com.berru.app.springbooth2.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    public ResponseEntity<GenreDTO> save(NewGenreRequestDTO newGenreRequestDTO) {
        Genre genre = genreMapper.toEntity(newGenreRequestDTO);
        Genre savedGenre = genreRepository.save(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(genreMapper.toDto(savedGenre));
    }

    public ResponseEntity<PaginationResponse<GenreDTO>> listPaginated(int pageNo, int pageSize) {
        List<Genre> genres = genreRepository.findAllWithMusics(); // Güncellenmiş metod

        // Listeyi sayfalama yapmak için Stream API kullanarak dönüşüm yapıyoruz
        int start = Math.min(pageNo * pageSize, genres.size());
        int end = Math.min(start + pageSize, genres.size());
        List<Genre> pagedGenres = genres.subList(start, end);

        List<GenreDTO> genreDTOList = pagedGenres.stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());

        PaginationResponse<GenreDTO> genrePaginationResponse = new PaginationResponse<>();
        genrePaginationResponse.setContent(genreDTOList);
        genrePaginationResponse.setPageNo(pageNo);
        genrePaginationResponse.setPageSize(pageSize);
        genrePaginationResponse.setTotalElements((long) genres.size());
        genrePaginationResponse.setTotalPages((int) Math.ceil((double) genres.size() / pageSize));
        genrePaginationResponse.setLast(end == genres.size());

        return ResponseEntity.ok(genrePaginationResponse);
    }

    public ResponseEntity<GenreDTO> getById(int id) {
        Genre genre = genreRepository.findByIdWithMusics(id);
        if (genre == null) {
            throw new NotFoundException("Genre not found");
        }
        return ResponseEntity.ok(genreMapper.toDto(genre));
    }

    public ResponseEntity<GenreDTO> update(int id, UpdateGenreRequestDTO updateGenreRequestDTO) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        genreMapper.updateGenreFromDto(updateGenreRequestDTO, genre);
        Genre updatedGenre = genreRepository.save(genre);

        return ResponseEntity.ok(genreMapper.toDto(updatedGenre));
    }

    public ResponseEntity<DeleteGenreResponseDTO> delete(int id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            DeleteGenreResponseDTO response = new DeleteGenreResponseDTO("Genre deleted successfully");
            return ResponseEntity.ok(response);
        }
        throw new NotFoundException("Genre not found");
    }
}
