package com.gamecrew.gamecrew_project.global.exception;

import org.springframework.http.HttpStatus;

public class MismatchException extends RuntimeException {
    private final HttpStatus status;

    public MismatchException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
