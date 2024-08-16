package com.berru.app.springbooth2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder // Lombok Builder Anotasyonu
@AllArgsConstructor // Tüm alanlar için constructor
@NoArgsConstructor  // Parametresiz constructor
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    @JsonBackReference
    private Genre genre;


    @JsonProperty("genre_name") // JSON'da genre_name olarak döner
    public String getGenreName() {
        return genre != null ? genre.getName() : null;
    }
}
