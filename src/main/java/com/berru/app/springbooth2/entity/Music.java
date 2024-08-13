package com.berru.app.springbooth2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data

public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false , unique=true )
    private String name ;

    @ManyToOne
    @JoinColumn(name = "genre_id") // @JoinColumn(name = "genre_id"): genre alanı için veritabanında genre_id adında bir foreign key sütunu oluşturur.
    //hangi sütunun yabancı anahtar (foreign key) olarak kullanılacağını belirtmek için kullanılır
    @JsonBackReference
    private Genre genre;

    // Bu metot, genre name'i JSON çıktısında ek olarak göstermeyi sağlar
    @JsonProperty("genre_name")
    public String getGenreName() {
        return genre != null ? genre.getName() : null;
    }


}
