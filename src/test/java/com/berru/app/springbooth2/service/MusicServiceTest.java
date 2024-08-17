package com.berru.app.springbooth2.service;

import com.berru.app.springbooth2.repository.GenreRepository;
import com.berru.app.springbooth2.repository.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;


public class MusicServiceTest {
    private MusicService musicService;

    private MusicRepository musicRepository;
    private GenreRepository genreRepository;

    @BeforeEach
    public void setUp() throws Exception {

        musicRepository = Mockito.mock(MusicRepository.class);
        genreRepository = Mockito.mock(GenreRepository.class);
        musicService = new MusicService(musicRepository, genreRepository);
    }
}
