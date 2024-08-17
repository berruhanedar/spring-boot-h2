package com.berru.app.springbooth2.service;

import com.berru.app.springbooth2.dto.MusicDTO;
import com.berru.app.springbooth2.dto.NewMusicRequestDTO;
import com.berru.app.springbooth2.dto.PaginationResponse;
import com.berru.app.springbooth2.dto.UpdateMusicRequestDTO;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.entity.Music;
import com.berru.app.springbooth2.exception.NotFoundException;
import com.berru.app.springbooth2.repository.GenreRepository;
import com.berru.app.springbooth2.repository.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
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

    @Test
    public void whenListCalledWithValidPageRequest_itShouldReturnPaginatedMusicDTOs() {
        int pageNo=0;
        int pageSize=2;

        Genre genre=new Genre();
        genre.setId(1);
        genre.setName("Rock");

        List<Music> musics = List.of(
                Music.builder().id(1).name("Rock Song 1").genre(genre).build(),
                Music.builder().id(2).name("Rock Song 2").genre(genre).build()
        );

        Page<Music> musicPage = new PageImpl<>(musics, PageRequest.of(pageNo, pageSize), 5);

        MusicDTO musicDTO1=new MusicDTO();
        musicDTO1.setId(1);
        musicDTO1.setName("Rock Song 1");
        musicDTO1.setGenreName("Rock");

        MusicDTO musicDTO2=new MusicDTO();
        musicDTO2.setId(2);
        musicDTO2.setName("Rock Song 2");
        musicDTO2.setGenreName("Rock");

        PaginationResponse<MusicDTO> expectedResponse = new PaginationResponse<>();
        expectedResponse.setContent(List.of(musicDTO1, musicDTO2));
        expectedResponse.setPageNo(0);
        expectedResponse.setPageSize(2);
        expectedResponse.setTotalElements(5);
        expectedResponse.setTotalPages(3); // 5 eleman, 2 eleman sayfada => 3 sayfa
        expectedResponse.setLast(false); // İlk sayfa, son sayfa değil// Mock davranışlarını ayarlayın
        when(musicRepository.findAllWithGenre(PageRequest.of(pageNo, pageSize))).thenReturn(musicPage);

        // Act
        PaginationResponse<MusicDTO> response = musicService.list(pageNo, pageSize);

        // Assert
        assertEquals(expectedResponse, response);

        // Verify
        verify(musicRepository).findAllWithGenre(PageRequest.of(pageNo, pageSize));
    }

    @Test
    public void whenUpdateCalledWithValidRequest_itShouldReturnUpdatedMusicDTO() {
        int musicId=1;
        UpdateMusicRequestDTO updateMusicRequestDTO=new UpdateMusicRequestDTO();
        updateMusicRequestDTO.setName("Updated Rock Song");
        updateMusicRequestDTO.setGenreId(2);

        Genre genre= new Genre();
        genre.setId(2);
        genre.setName("Pop");

        Music existingMusic= Music.builder()
                .id(musicId)
                .name("Rock Song")
                .genre(new Genre())
                .build();

        Music updatedMusic= Music.builder()
                .id(musicId)
                .name("Updated Rock Song")
                .genre(genre)
                .build();

        MusicDTO musicDTO=new MusicDTO();
        musicDTO.setId(musicId);
        musicDTO.setName("Updated Rock Song");
        musicDTO.setGenreName("Pop");

        // Mock davranışlarını ayarlayın
        when(musicRepository.findByIdWithGenre(musicId)).thenReturn(existingMusic);
        when(genreRepository.findById(updateMusicRequestDTO.getGenreId())).thenReturn(Optional.of(genre));
        when(musicRepository.save(updatedMusic)).thenReturn(updatedMusic);

        // Act
        ResponseEntity<MusicDTO> response = musicService.update(musicId, updateMusicRequestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(musicDTO, response.getBody());

        // Verify
        verify(musicRepository).findByIdWithGenre(musicId);
        verify(genreRepository).findById(updateMusicRequestDTO.getGenreId());
        verify(musicRepository).save(updatedMusic);
    }
    @Test
    public void whenUpdateCalledWithInvalidMusicId_itShouldThrowNotFoundException() {
        int invalidMusicId=999;
        UpdateMusicRequestDTO updateMusicRequestDTO=new UpdateMusicRequestDTO();
        updateMusicRequestDTO.setName("Updated Song");
        updateMusicRequestDTO.setGenreId(1);

        // Mock davranışını ayarla
        when(musicRepository.findByIdWithGenre(invalidMusicId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            musicService.update(invalidMusicId, updateMusicRequestDTO);
        });
    }

    @Test
    public void whenUpdateCalledWithInvalidGenreId_itShouldThrowNotFoundException() {
        int musicId=1;
        UpdateMusicRequestDTO updateMusicRequestDTO= new UpdateMusicRequestDTO();
        updateMusicRequestDTO.setName("Updated Song");
        updateMusicRequestDTO.setGenreId(999);
        Music existingMusic=new Music();
        existingMusic.setId(musicId);
        existingMusic.setName("Old Song");
        existingMusic.setGenre(new Genre());

        // Mock davranışlarını ayarla
        when(musicRepository.findByIdWithGenre(musicId)).thenReturn(existingMusic);
        when(genreRepository.findById(updateMusicRequestDTO.getGenreId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            musicService.update(musicId, updateMusicRequestDTO);
        });
    }

}
