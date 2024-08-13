package com.berru.app.springbooth2.dto;

public class DeleteResponseDTO {private String message;

    public DeleteResponseDTO(String message) {
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
