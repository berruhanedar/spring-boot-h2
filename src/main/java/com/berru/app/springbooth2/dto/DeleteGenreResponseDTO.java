package com.berru.app.springbooth2.dto;

public class DeleteGenreResponseDTO {private String message;

    public DeleteGenreResponseDTO(String message) {
        this.message = message;
    }

    // Getter ve Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
