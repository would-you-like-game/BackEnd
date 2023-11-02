package com.gamecrew.gamecrew_project.global.exception;

import org.springframework.http.HttpStatus;

public interface CustomChatException {
    HttpStatus getHttpStatus();
    String getErrorMsg();
}
