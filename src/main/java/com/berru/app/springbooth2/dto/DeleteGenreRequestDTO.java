package com.berru.app.springbooth2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteGenreRequestDTO {
    @NotNull(message = "id is mandatory")
    private Integer id;
    private String message;

    public DeleteGenreRequestDTO(Integer id, String message) {
        this.id = id;
        this.message = message;
    }
}
