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
import org.springframework.web.server.ResponseStatusException;

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

        //Nesne Oluşturma Sürecini Basitleştirmek
        // Lombok Builder ile DTO'dan bir Music nesnesi oluşturur
        Music music = Music.builder()
                .name(newMusicRequestDTO.getName())
                .genre(genre)
                .build();

        // Müzik nesnesini veritabanına kaydeder
        Music savedMusic = musicRepository.save(music);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedMusic));
    }


    //dış dünyaya veri gönderirken yapılan bir işlemdir.
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
        try {
            // Music ID kontrolü
            Music existingMusic = musicRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Music not found"));

            // Genre ID kontrolü
            Genre genre = genreRepository.findById(updateMusicRequestDTO.getGenreId())
                    .orElseThrow(() -> new NotFoundException("Genre not found"));

            // Müzik adını güncelleme
            existingMusic.setName(updateMusicRequestDTO.getName());
            existingMusic.setGenre(genre);

            // Müzik kaydını güncelleme
            Music updatedMusic = musicRepository.save(existingMusic);

            return ResponseEntity.ok(convertToDTO(updatedMusic));
        } catch (IllegalArgumentException ex) {
            // İstemci hatası durumunda 400 Bad Request döndürülmesi
            return ResponseEntity.badRequest().body(null);
        }
    }



    // Müzik silme işlemi
    public ResponseEntity<Void> delete(int id) {
        if (musicRepository.existsById(id)) {
            musicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new NotFoundException("Music not found");
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