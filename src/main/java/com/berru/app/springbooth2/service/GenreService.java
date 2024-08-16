package com.berru.app.springbooth2.service;

import com.berru.app.springbooth2.dto.*;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.repository.GenreRepository;
import com.berru.app.springbooth2.mapper.GenreMapper; // MapStruct Mapper sınıfını dahil ettik
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public ResponseEntity<PaginationResponse<GenreDTO>> listPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Genre> genrePage = genreRepository.findAll(pageable);

        List<GenreDTO> genreDTOList = genrePage.getContent().stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());

        PaginationResponse<GenreDTO> genrePaginationResponse = new PaginationResponse<>();
        genrePaginationResponse.setContent(genreDTOList);
        genrePaginationResponse.setPageNo(genrePage.getNumber());
        genrePaginationResponse.setPageSize(genrePage.getSize());
        genrePaginationResponse.setTotalElements(genrePage.getTotalElements());
        genrePaginationResponse.setTotalPages(genrePage.getTotalPages());
        genrePaginationResponse.setLast(genrePage.isLast());

        return ResponseEntity.ok(genrePaginationResponse);
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

    public ResponseEntity<DeleteGenreResponseDTO> delete(int id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            DeleteGenreResponseDTO response = new DeleteGenreResponseDTO("Genre deleted successfully");
            return ResponseEntity.ok(response); // HTTP 200 OK ile yanıt döndürür
        }
        throw new NotFoundException("Genre not found");
    }


}
