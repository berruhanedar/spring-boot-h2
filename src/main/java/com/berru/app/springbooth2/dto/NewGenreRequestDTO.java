package com.berru.app.springbooth2.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewGenreRequestDTO {
    @Valid
    @NotNull(message = "name is mandatory")
    private String name;
}
