package com.berru.app.springbooth2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
// Bu anotasyon, bu istisna fırlatıldığında HTTP 400 kodu döndürüleceğini belirtir.
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}