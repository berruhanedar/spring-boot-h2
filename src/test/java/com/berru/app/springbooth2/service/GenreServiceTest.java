package com.berru.app.springbooth2.service;


import com.berru.app.springbooth2.dto.GenreDTO;
import com.berru.app.springbooth2.dto.NewGenreRequestDTO;
import com.berru.app.springbooth2.dto.PaginationResponse;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.exception.NotFoundException;
import com.berru.app.springbooth2.mapper.GenreMapper;
import com.berru.app.springbooth2.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GenreServiceTest {

    private GenreService genreService;

    private GenreRepository genreRepository;
    private GenreMapper genreMapper;

    @BeforeEach
    public void setUp() throws Exception {

        genreRepository = Mockito.mock(GenreRepository.class);
        genreMapper = Mockito.mock(GenreMapper.class);

        genreService = new GenreService(genreRepository, genreMapper);
    }
    @Test
    public void whenSaveCalledWithValidRequest_itShouldReturnValidGenreDTO() {
        NewGenreRequestDTO newGenreRequestDTO  = new NewGenreRequestDTO();
        newGenreRequestDTO.setName("Heavy Metal");

        Genre genre = new Genre();
        genre.setName("Heavy Metal");

        Genre savedGenre = new Genre();
        savedGenre.setId(1); //Simüle edilen Id
        savedGenre.setName("Heavy Metal");

        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(1);
        genreDTO.setName("Heavy Metal");

        // Mock davranışlarını ayarlayın
        when(genreMapper.toEntity(newGenreRequestDTO)).thenReturn(genre);
        when(genreRepository.save(genre)).thenReturn(savedGenre);
        when(genreMapper.toDto(savedGenre)).thenReturn(genreDTO);

        // Act
        ResponseEntity<GenreDTO> response = genreService.save(newGenreRequestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(genreDTO, response.getBody());

        // Doğrulama
        verify(genreMapper).toEntity(newGenreRequestDTO);
        verify(genreRepository).save(genre);
        verify(genreMapper).toDto(savedGenre);

    }

    @Test
    public void whenListPaginatedWithValidRequest_itShouldReturnPaginatedGenreDTO() {
        int pageNo=1;
        int pageSize=2;

        List<Genre> genres = IntStream.range(0, 5) // 5 tane Genre nesnesi oluşturuyoruz
                .mapToObj(i -> {
                    Genre genre=new Genre();
                    genre.setId(i + 1);
                    genre.setName("Genre " + (i + 1));
                    return genre;
                })
                .collect(Collectors.toList());

        List<GenreDTO> genreDTOs = genres.stream()
                .map(genre -> {
                    GenreDTO dto= new GenreDTO();
                    dto.setId(genre.getId());
                    dto.setName(genre.getName());
                    return dto;
                })
                .collect(Collectors.toList());

        when(genreRepository.findAllWithMusics()).thenReturn(genres);
        when(genreMapper.toDto(any(Genre.class))).thenAnswer(invocation -> {
            Genre genre= invocation.getArgument(0);
            GenreDTO dto= new GenreDTO();
            dto.setId(genre.getId());
            dto.setName(genre.getName());
            return dto;
        });

        // Act
        ResponseEntity<PaginationResponse<GenreDTO>> response = genreService.listPaginated(pageNo, pageSize);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PaginationResponse<GenreDTO> paginationResponse = response.getBody();
        assertNotNull(paginationResponse);
        assertEquals(pageNo, paginationResponse.getPageNo());
        assertEquals(pageSize, paginationResponse.getPageSize());
        assertEquals(5L, paginationResponse.getTotalElements());
        assertEquals(3, paginationResponse.getTotalPages());
        assertEquals(false, paginationResponse.isLast()); // Sayfanın son sayfa olup olmadığını kontrol eder

        List<GenreDTO> content = paginationResponse.getContent();
        assertEquals(2, content.size()); // İkinci sayfada 2 öğe bekliyoruz
        assertEquals("Genre 3", content.get(0).getName());
        assertEquals("Genre 4", content.get(1).getName());
    }

    @Test
    public void whenGetByIdWithValidId_itShouldReturnGenreDTO() {
        int id=1;

        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("Heavy Metal");

        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(id);
        genreDTO.setName("Heavy Metal");

        when(genreRepository.findById(id)).thenReturn(java.util.Optional.of(genre));
        when(genreMapper.toDto(genre)).thenReturn(genreDTO);

        // Act
        ResponseEntity<GenreDTO> response = genreService.getById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genreDTO, response.getBody());

        // Verify
        verify(genreRepository).findById(id);
        verify(genreMapper).toDto(genre);
    }
    @Test
    public void whenGetByIdWithInvalidId_itShouldThrowNotFoundException() {
        int id=1;  // id değişkenini burada tanımlıyoruz

        when(genreRepository.findById(id)).thenReturn(java.util.Optional.empty());

        NotFoundException thrown= assertThrows(NotFoundException.class, () -> {
            genreService.getById(id);
        });
        assertEquals("Genre not found", thrown.getMessage());

        // Verify
        verify(genreRepository).findById(id);
        verify(genreMapper, never()).toDto(any(Genre.class));
    }





}

