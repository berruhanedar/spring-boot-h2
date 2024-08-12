package com.berru.app.springbooth2.entities;
//Entity layer projenin yapı taşıdır . Entity'leri database tabloları gibi düşün
//Code First yaklaşımı ile ilerliyoruz yani bu tarafa bakıp arkada database JPA yardımıyla oluşturmuş olacak projemizi

import com.fasterxml.jackson.annotation.JsonManagedReference; //@JsonManagedReference . JSON serileştirmesi sırasında döngüsel referansları yönetmek için kullanılır.
import jakarta.persistence.*; //@Entity , @Id , @GeneratedValue , @Column , @OneToMany .  JPA (Java Persistence API) anotasyonlarını ve sınıflarını kullanmak için gerekli olan paket. Veritabanı işlemleri için gerekli bileşenleri içerir.
import lombok.Data; //@Data
import java.util.List;


@Entity //Springe buranın bir entity olduğunu anlatmak için @Entity anotasyonunu ekliyoruz
@Data  // İstemciden buradaki Entitye göre veri alacağımız için data anotasyonu ekliyoruz . Lombok tarafından sağlanan bu anotasyon, sınıf için otomatik olarak getter, setter metotlarını oluşturur.


public class Genre { //Classımız tablo içine yazacaklarımız columnlarımız olacak
    @Id // Veritabanında primary key olarak id kullanacağımız için @Id anotasyonunu ekliyoruz
    @GeneratedValue(strategy = GenerationType.AUTO) //GenerationType.AUTO: JPA, mevcut veritabanına en uygun stratejiyi otomatik olarak seçer.
    //@GeneratedValue, birincil anahtar olan bir filedın değerini otomatik olarak oluşturmak için kullanılan bir JPA (Java Persistence API) anotasyonudur. AUTO= JPA'nın mevcut veritabanına en uygun stratejiyi otomatik olarak seçmesini sağlar.
    private int id;


    @Column(nullable = false , unique=true ) //Kısıtlamalar için kullanırız
    private String name ;


    //OneToMany kullanma sebebimiz Genre entitysi birCascade (kapsama) özelliği, bir entity üzerinde yapılan değişikliklerin bağlı entity'lere otomatik olarak uygulanmasını sağlar
    //Cascade (kapsama) özelliği, bir entity üzerinde yapılan değişikliklerin bağlı entity'lere otomatik olarak uygulanmasını sağlar
    //mappedBy, bu ilişkinin hangi taraftan yönetildiğini belirtir. Yani, ilişkinin sahibi (owner) kimdir, onu belirtir.
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    @JsonManagedReference // Bu anotasyon, JSON serileştirmesinde döngüsel referansları önlemek için kullanılır.  Yani, Genre ve Music arasında bir döngüsel ilişki varsa, bu anotasyon serileştirme sırasında döngüleri kırar.
    private List<Music> musics;
    //Bu, Genre sınıfının bir List koleksiyonunu (Music nesnelerinden oluşan bir liste) tutacağını belirtir. Bu liste, bir Genre nesnesine bağlı olan tüm Music nesnelerini temsil eder.
    //Bu sınıfta, bir müzik türüne ait tüm şarkıları (Music nesnelerini) tutan bir liste vardır.
    //Bu ilişki, Music sınıfındaki genre alanı tarafından yönetilir. Yani, her Music nesnesi, hangi Genre'ye ait olduğunu genre alanı üzerinden belirtir.


}
