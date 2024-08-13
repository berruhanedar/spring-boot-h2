package com.berru.app.springbooth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MusicDTO {
    private int id;
    private String name;

    @JsonProperty("genre_name")
    private String genreName;
}
