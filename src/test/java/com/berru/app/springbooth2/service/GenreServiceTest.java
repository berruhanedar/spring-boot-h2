package com.berru.app.springbooth2.service;


import com.berru.app.springbooth2.mapper.GenreMapper;
import com.berru.app.springbooth2.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

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

}

