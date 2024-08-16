package com.berru.app.springbooth2.service;

import com.berru.app.springbooth2.dto.MusicDTO;
import com.berru.app.springbooth2.dto.MusicPaginationResponse;
import com.berru.app.springbooth2.dto.NewMusicRequestDTO;
import com.berru.app.springbooth2.dto.UpdateMusicRequestDTO;
import com.berru.app.springbooth2.exception.NotFoundException;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.entity.Music;
import com.berru.app.springbooth2.repository.GenreRepository;
import com.berru.app.springbooth2.repository.MusicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicService {
    private final MusicRepository musicRepository;
    private final GenreRepository genreRepository;

    public MusicService(MusicRepository musicRepository, GenreRepository genreRepository) {
        this.musicRepository = musicRepository;
        this.genreRepository = genreRepository;
    }

    public ResponseEntity<MusicDTO> save(NewMusicRequestDTO newMusicRequestDTO) {
        Genre genre = genreRepository.findById(newMusicRequestDTO.getGenreId())
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        Music music = Music.builder()
                .name(newMusicRequestDTO.getName())
                .genre(genre)
                .build();

        Music savedMusic = musicRepository.save(music);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedMusic));
    }

    private MusicDTO convertToDTO(Music music) {
        MusicDTO musicDTO = new MusicDTO();
        musicDTO.setId(music.getId());
        musicDTO.setName(music.getName());
        musicDTO.setGenreName(music.getGenreName());
        return musicDTO;
    }

    public MusicPaginationResponse list(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Music> musicPage = musicRepository.findAll(pageable);

        List<MusicDTO> musicDTOList = musicPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        MusicPaginationResponse musicPaginationResponse = new MusicPaginationResponse();
        musicPaginationResponse.setContent(musicDTOList);
        musicPaginationResponse.setPageNo(musicPage.getNumber());
        musicPaginationResponse.setPageSize(musicPage.getSize());
        musicPaginationResponse.setTotalElements(musicPage.getTotalElements());
        musicPaginationResponse.setTotalPages(musicPage.getTotalPages());
        musicPaginationResponse.setLast(musicPage.isLast());

        return musicPaginationResponse;
    }

    public ResponseEntity<MusicDTO> getById(int id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Music not found"));

        return ResponseEntity.ok(convertToDTO(music));
    }

    public ResponseEntity<MusicDTO> update(int id, UpdateMusicRequestDTO updateMusicRequestDTO) {
        try {
            Music existingMusic = musicRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Music not found"));

            Genre genre = genreRepository.findById(updateMusicRequestDTO.getGenreId())
                    .orElseThrow(() -> new NotFoundException("Genre not found"));

            existingMusic.setName(updateMusicRequestDTO.getName());
            existingMusic.setGenre(genre);

            Music updatedMusic = musicRepository.save(existingMusic);

            return ResponseEntity.ok(convertToDTO(updatedMusic));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    public ResponseEntity<Void> delete(int id) {
        if (musicRepository.existsById(id)) {
            musicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new NotFoundException("Music not found");
    }

    public ResponseEntity<List<MusicDTO>> getByGenre(int genreId) {
        List<Music> musics = musicRepository.findByGenreId(genreId);
        List<MusicDTO> musicDTOs = musics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(musicDTOs);
    }
}
