package com.gamecrew.gamecrew_project.global.exception;

import org.springframework.http.HttpStatus;

public class FailedToSendEmailException extends RuntimeException {
    private final HttpStatus status;

    public FailedToSendEmailException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
