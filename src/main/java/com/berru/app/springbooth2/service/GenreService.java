package com.berru.app.springbooth2.service;
//"BUSINESS LOGIC"" olduğu kısımdır = VERİLER BURADA İŞLENİR!!!!!
//dışardan controllera gelen isteği alıp kendi business logicimize entegre edip verileri düzenlediğmiz dönüş değerini oluşturacağımız yapı olacak

import com.berru.app.springbooth2.dto.DeleteResponseDTO;
import com.berru.app.springbooth2.dto.GenreDTO;
import com.berru.app.springbooth2.dto.NewGenreRequestDTO;
import com.berru.app.springbooth2.dto.UpdateGenreRequestDTO;
import com.berru.app.springbooth2.entity.Genre;
import com.berru.app.springbooth2.repository.GenreRepository;
import org.springframework.http.HttpStatus; // HTTP durum kodlarını (status codes) temsil eden HttpStatus sınıfını projeye dahil eder
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity; //Bu satır, HTTP yanıtlarını kapsülleyen ve HTTP durum kodu, başlıklar ve içerik (body) gibi bileşenleri içeren bir ResponseEntity sınıfını projeye dahil eder. Bu, metotların daha esnek ve özelleştirilebilir HTTP yanıtları döndürmesine olanak tanır.
import java.util.List;
import com.berru.app.springbooth2.exception.NotFoundException;


@Service //Spring, bu sınıfı bir Bean olarak yönetir ve bu sınıfın iş mantığını içerdiğini gösterir.  Bean, Spring tarafından oluşturulan, yönetilen ve yaşam döngüsü kontrol edilen bir nesnedir.
public class GenreService {
    //final anahtar kelimesi, Java'da bir değişken, metod veya sınıfın son halini belirlemek için kullanılır.
    final GenreRepository genreRepository;
    //genreRepository Değişkeni
    //genreRepository bir GenreRepository türündeki değişkendir. Bu değişken, GenreRepository arayüzünün bir implementasyonunu (uygulamasını) tutar.
    //"Bean" ise Spring tarafından oluşturulan, yönetilen ve yaşam döngüsü kontrol edilen bir nesnedir. Yani, bu sınıf bir "bean" olarak yönetilir.

    //Constructor, genreRepository değişkenini başlatır. final değişkenler sadece constructor üzerinden atanabilir.
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    //Bu metot, genreRepository değişkenini başlatmak için kullanılır. GenreRepository türündeki bu değişken, GenreService sınıfının oluşturulması sırasında parametre olarak alınır ve sınıfın genreRepository değişkenine atanır.
    //save metodu, bir Genre nesnesini veritabanına kaydeder.

    private GenreDTO convertToDTO(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        return genreDTO;
    }

    public ResponseEntity<GenreDTO> save(NewGenreRequestDTO newGenreRequestDTO) {
        Genre genre = new Genre();
        genre.setName(newGenreRequestDTO.getName());

        Genre savedGenre = genreRepository.save(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedGenre));
    }

    //list metodu, tüm Genre nesnelerini veritabanından alır ve bir liste olarak döndürür.
    public ResponseEntity<List<Genre>> list() {
        List<Genre> genres = genreRepository.findAll();
        return ResponseEntity.ok(genres);
    }

    //getById metodu, verilen id'ye sahip bir Genre nesnesini getirir. Eğer nesne bulunamazsa, null döner.
    public ResponseEntity<GenreDTO> getById(int id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found"));
        return ResponseEntity.ok(convertToDTO(genre));
    }


    //update metodu, verilen id'ye sahip bir Genre nesnesi varsa günceller ve güncellenmiş nesneyi döner. Eğer nesne bulunamazsa, null döner.
    public ResponseEntity<GenreDTO> update(int id, UpdateGenreRequestDTO updateGenreRequestDTO) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found"));

        genre.setName(updateGenreRequestDTO.getName());
        Genre updatedGenre = genreRepository.save(genre);

        return ResponseEntity.ok(convertToDTO(updatedGenre));
    }


    //delete metodu, verilen id'ye sahip Genre nesnesini veritabanından siler.
    public ResponseEntity<DeleteResponseDTO> delete(int id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new DeleteResponseDTO("Genre deleted successfully"));
        }
        throw new NotFoundException("Genre not found");
    }


}
