package com.berru.app.springbooth2.dto;


import lombok.Data;

import java.util.List;

@Data
public class GenreDTO {
    private int id;
    private String name;
    private List<MusicDTO> musics;

}


