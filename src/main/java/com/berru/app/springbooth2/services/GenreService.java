package com.berru.app.springbooth2.services;
//"BUSINESS LOGIC"" olduğu kısımdır = VERİLER BURADA İŞLENİR!!!!!
//dışardan controllera gelen isteği alıp kendi business logicimize entegre edip verileri düzenlediğmiz dönüş değerini oluşturacağımız yapı olacak

import com.berru.app.springbooth2.entities.Genre;
import com.berru.app.springbooth2.repositories.GenreRepository;
import org.springframework.http.HttpStatus; // HTTP durum kodlarını (status codes) temsil eden HttpStatus sınıfını projeye dahil eder
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity; //Bu satır, HTTP yanıtlarını kapsülleyen ve HTTP durum kodu, başlıklar ve içerik (body) gibi bileşenleri içeren bir ResponseEntity sınıfını projeye dahil eder. Bu, metotların daha esnek ve özelleştirilebilir HTTP yanıtları döndürmesine olanak tanır.
import java.util.List;

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
    public ResponseEntity<Genre> save(Genre genre) {
        Genre savedGenre = genreRepository.save(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
    }

    //list metodu, tüm Genre nesnelerini veritabanından alır ve bir liste olarak döndürür.
    public ResponseEntity<List<Genre>> list() {
        List<Genre> genres = genreRepository.findAll();
        return ResponseEntity.ok(genres);
    }

    //getById metodu, verilen id'ye sahip bir Genre nesnesini getirir. Eğer nesne bulunamazsa, null döner.
    public ResponseEntity<Genre> getById(int id) {
        return genreRepository.findById(id)
                .map(genre -> ResponseEntity.ok(genre))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    //update metodu, verilen id'ye sahip bir Genre nesnesi varsa günceller ve güncellenmiş nesneyi döner. Eğer nesne bulunamazsa, null döner.
    public ResponseEntity<Genre> update(int id, Genre genre) {
        if (genreRepository.existsById(id)) {
            genre.setId(id);
            Genre updatedGenre = genreRepository.save(genre);
            return ResponseEntity.ok(updatedGenre);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //delete metodu, verilen id'ye sahip Genre nesnesini veritabanından siler.
    public ResponseEntity<Void> delete(int id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
