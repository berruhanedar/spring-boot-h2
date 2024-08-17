package com.berru.app.springbooth2.service;


import com.berru.app.springbooth2.dto.GenreDTO;
import com.berru.app.springbooth2.dto.NewGenreRequestDTO;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.mapper.GenreMapper;
import com.berru.app.springbooth2.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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


}

