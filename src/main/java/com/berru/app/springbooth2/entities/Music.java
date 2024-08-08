package com.berru.app.springbooth2.entities;

//Entity katmanını database tabloları gibi düşün

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity //Springe buranın bir entity olduğunu anlatmak için @Entity anotasyonunu ekliyoruz
@Data  //Entitye göre veri alacağımız için data anotasyonu ekliyoruz

public class Music { //Classımız tablo içine yazacaklarımız columnlarımız olacak
    @Id // Veritabanında primary key olarak id kullanacağımız için @Id anotasyonunu ekliyoruz
    @GeneratedValue(strategy = GenerationType.AUTO) //bir sütunun otomatik olarak değerinin oluşturulmasını sağlar. Genellikle birincil anahtar (primary key) alanları için kullanılır
    private int id;

    @Column(nullable = false , unique=true ) //Kısıtlamalar için kullanırız
    private String name ;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    @JsonBackReference
    private Genre genre;

    // Bu metot, genre name'i JSON çıktısında ek olarak göstermeyi sağlar
    @JsonProperty("genre_name")
    public String getGenreName() {
        return genre != null ? genre.getName() : null;
    }


}
