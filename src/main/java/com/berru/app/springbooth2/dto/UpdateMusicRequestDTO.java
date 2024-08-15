package com.berru.app.springbooth2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.Pattern;


@Data
public class UpdateMusicRequestDTO {
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Music name can only contain letters and spaces.")
    @NotNull(message = "name is mandatory")
    private String name;


    @NotNull(message = "genreId is mandatory")
    private Integer genreId;
}
