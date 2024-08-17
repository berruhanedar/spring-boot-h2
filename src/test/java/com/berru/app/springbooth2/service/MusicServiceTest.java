package com.berru.app.springbooth2.service;

import com.berru.app.springbooth2.dto.MusicDTO;
import com.berru.app.springbooth2.dto.NewMusicRequestDTO;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.entity.Music;
import com.berru.app.springbooth2.exception.NotFoundException;
import com.berru.app.springbooth2.repository.GenreRepository;
import com.berru.app.springbooth2.repository.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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
    @Test
    public void whenSaveCalledWithValidRequest_itShouldReturnValidMusicDTO() {
        int genreId=1;
        NewMusicRequestDTO newMusicRequestDTO =new NewMusicRequestDTO() ;
        newMusicRequestDTO.setName("Rock Song");
        newMusicRequestDTO.setGenreId(genreId);

        Genre genre= new Genre();
        genre.setId(genreId);
        genre.setName("Rock");

        Music music= Music.builder()
                .name("Rock Song")
                .genre(genre)
                .build();

        Music savedMusic= Music.builder()
                .id(1) // Simüle edilen ID
                .name("Rock Song")
                .genre(genre)
                .build();

        MusicDTO musicDTO = new MusicDTO();
        musicDTO.setId(1); // Simüle edilen ID
        musicDTO.setName("Rock Song");
        musicDTO.setGenreName("Rock");

        // Mock davranışlarını ayarlayın
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(musicRepository.save(music)).thenReturn(savedMusic);

        // Act
        ResponseEntity<MusicDTO> response = musicService.save(newMusicRequestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(musicDTO, response.getBody());

        // Verify
        verify(genreRepository).findById(genreId);
        verify(musicRepository).save(music);
    }

    @Test
    public void whenSaveCalledWithInvalidGenreId_itShouldThrowNotFoundException() {
        int genreId=1;
        NewMusicRequestDTO newMusicRequestDTO=new NewMusicRequestDTO();
        newMusicRequestDTO.setName("Rock Song");
        newMusicRequestDTO.setGenreId(genreId);

        // Mock davranışlarını ayarlayın
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        NotFoundException thrown= assertThrows(NotFoundException.class, () -> {
            musicService.save(newMusicRequestDTO);
        });
        assertEquals("Genre not found", thrown.getMessage());

        // Verify
        verify(genreRepository).findById(genreId);
        verify(musicRepository, never()).save(any(Music.class));
    }

    @Test
    public void whenGetByIdCalledWithValidId_itShouldReturnValidMusicDTO() {
        int musicId=1;
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Rock");

        Music music= Music.builder()
                .id(musicId)
                .name("Rock Song")
                .genre(genre)
                .build();

        MusicDTO musicDTO=new MusicDTO();
        musicDTO.setId(musicId);
        musicDTO.setName("Rock Song");
        musicDTO.setGenreName("Rock");

        // Mock davranışlarını ayarlayın
        when(musicRepository.findByIdWithGenre(musicId)).thenReturn(music);

        // Act
        ResponseEntity<MusicDTO> response = musicService.getById(musicId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(musicDTO, response.getBody());

        // Verify
        verify(musicRepository).findByIdWithGenre(musicId);
    }



}
