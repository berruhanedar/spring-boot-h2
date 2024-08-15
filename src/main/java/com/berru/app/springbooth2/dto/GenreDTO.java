package com.berru.app.springbooth2.dto;


import lombok.Data;

import java.util.List;

@Data
public class GenreDTO {
    private int id;
    private String name;
    private List<MusicDTO> musics;  // Müzikleri içerir

    // Getters ve Setters
}



//Amaç: Genellikle uygulamanın farklı katmanları arasında veri taşırken veya API yanıtları oluştururken kullanılır.

