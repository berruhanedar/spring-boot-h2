package com.berru.app.springbooth2.entities;
//Entity katmanını database tabloları gibi düşün

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity //Springe buranın bir entity olduğunu anlatmak için @Entity anotasyonunu ekliyoruz
@Data  //Entitye göre veri alacağımız için data anotasyonu ekliyoruz

public class Genre { //Classımız tablo içine yazacaklarımız columnlarımız olacak
    @Id // Veritabanında primary key olarak id kullanacağımız için @Id anotasyonunu ekliyoruz
    @GeneratedValue(strategy = GenerationType.AUTO) //bir sütunun otomatik olarak değerinin oluşturulmasını sağlar. Genellikle birincil anahtar (primary key) alanları için kullanılır

    private int id;

    @Column(nullable = false , unique=true ) //Kısıtlamalar için kullanırız
    private String name ;


    //OneToMany kullanma sebebimiz Genre entitysi birCascade (kapsama) özelliği, bir entity üzerinde yapılan değişikliklerin bağlı entity'lere otomatik olarak uygulanmasını sağlar
    //Cascade (kapsama) özelliği, bir entity üzerinde yapılan değişikliklerin bağlı entity'lere otomatik olarak uygulanmasını sağlar
    //mappedBy özelliği, bu ilişkinin Music entity'sinde genre alanı tarafından yönetildiğini belirtir
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Music> musics;

}
