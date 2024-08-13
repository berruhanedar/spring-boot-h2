package com.berru.app.springbooth2.service;

import com.berru.app.springbooth2.dto.MusicDTO;
import com.berru.app.springbooth2.dto.NewMusicRequestDTO;
import com.berru.app.springbooth2.dto.UpdateMusicRequestDTO;
import com.berru.app.springbooth2.exception.NotFoundException;

import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.entity.Music;
import com.berru.app.springbooth2.repository.GenreRepository;
import com.berru.app.springbooth2.repository.MusicRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicService {
    final MusicRepository musicRepository;
    final GenreRepository genreRepository;

    public MusicService(MusicRepository musicRepository, GenreRepository genreRepository) {
        this.musicRepository = musicRepository;
        this.genreRepository = genreRepository;
    }

    public ResponseEntity<MusicDTO> save(NewMusicRequestDTO newMusicRequestDTO) {
        // Genre ID'sine sahip bir Genre nesnesi arar
        Genre genre = genreRepository.findById(newMusicRequestDTO.getGenreId())
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        // DTO'dan bir Music nesnesi oluşturur
        Music music = new Music();
        music.setName(newMusicRequestDTO.getName());
        music.setGenre(genre);

        // Müzik nesnesini veritabanına kaydeder
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


    // Müzik nesnesini veritabanına kaydeder ve HTTP 201 (Created) yanıtı döner.


    public ResponseEntity<List<MusicDTO>> list() {
        List<Music> musics = musicRepository.findAll();
        List<MusicDTO> musicDTOs = musics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(musicDTOs);
    }


    // Belirli bir müziği ID'ye göre getir
    public ResponseEntity<MusicDTO> getById(int id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Music not found"));

        return ResponseEntity.ok(convertToDTO(music));

    }

    // Belirli bir müziği ID'ye göre güncelle
    public ResponseEntity<MusicDTO> update(int id, @Valid UpdateMusicRequestDTO updateMusicRequestDTO) {
        Music existingMusic = musicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Music not found"));

        existingMusic.setName(updateMusicRequestDTO.getName());
        Genre genre = genreRepository.findById(updateMusicRequestDTO.getGenreId())
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        existingMusic.setGenre(genre);
        Music updatedMusic = musicRepository.save(existingMusic);

        return ResponseEntity.ok(convertToDTO(updatedMusic));
    }


    // Müzik silme işlemi
    public ResponseEntity<Void> delete(int id) {
        if (musicRepository.existsById(id)) {
            musicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Belirli bir genre'e ait müzikleri getirme işlemi
    public ResponseEntity<List<MusicDTO>> getByGenre(int genreId) {
        List<Music> musics = musicRepository.findByGenreId(genreId);
        List<MusicDTO> musicDTOs = musics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(musicDTOs);
    }
}