package com.berru.app.springbooth2.dto;

import lombok.Data;

@Data
public class DeleteMusicResponseDTO {
    private String message;

    // Parametreli yapıcı (constructor) ekleyin
    public DeleteMusicResponseDTO(String message) {
        this.message = message;
    }

    // Varsayılan yapıcı (constructor) ekleyin
    public DeleteMusicResponseDTO() {}
}
