package com.gamecrew.gamecrew_project.global.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final boolean result;

    public CustomException(String message, HttpStatus status, boolean isDuplicate) {
        super(message);
        this.status = status;
        this.result = isDuplicate;
    }
}
