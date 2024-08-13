package com.berru.app.springbooth2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteMusicRequestDTO {
    @NotNull(message = "id is mandatory")
    private Integer id;
}
