package com.berru.app.springbooth2.repository;
//Veri erişim katmanı
//Repository database tarafıdır
//Repository katmanı, veri tabanı işlemlerini yönetir. Veriyi kaydetme, güncelleme, silme ve alma gibi işlemleri içerir.

import com.berru.app.springbooth2.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
//Java Liste Yapısı: Java'da, List<Music> türündeki alan, bu ilişkileri yönetmek ve veritabanında Genre nesnesine ait Music nesnelerini topluca almak, eklemek veya güncellemek için kullanılır. Liste, ORM (Object-Relational Mapping) ile veritabanı ile olan ilişkileri Java nesneleri seviyesinde yönetir.


public interface GenreRepository extends JpaRepository<Genre,Integer > {
}

//interface olma sebebi bir method içerisini doldurmadan ilerleyeceğiz
//GenreRepository Interface: Genre varlığı ile veri tabanında etkileşim kurmak için kullanılan bir repository arayüzüdür.
//Interface (Arayüz): Java'da bir interface, yalnızca metod imzalarını tanımlar; yani metodların isimlerini, parametrelerini ve dönüş türlerini belirtir, ancak bu metodların nasıl çalıştığını içermez. Bir sınıf, bir interface'i implement (uygulama) ederek, o interface'de tanımlanan metodları somutlaştırmak zorundadır.
//Integer: Bu, Genre entity'sinin birincil anahtarının (ID alanının) veri türüdür. Yani Genre sınıfında tanımlı olan birincil anahtar alanı Integer veri türündedir.



//İstek Alınır: Kullanıcıdan gelen HTTP isteği, Controller sınıfına yönlendirilir.
//Veri İşlenir: Controller, istekteki veriyi alır ve Service katmanına iletir.
//İş Mantığı Uygulanır: Service, gelen veriyi işler ve gerekli işlemleri yapar.
//Veri Tabanı İşlemleri: Service, veri tabanı işlemleri için Repository'yi kullanır ve veriyi veri tabanına kaydeder veya veri tabanından alır.
//Sonuç Döner: İşlemler tamamlandıktan sonra, sonuç Controller aracılığıyla kullanıcıya geri döner.