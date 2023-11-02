package com.gamecrew.gamecrew_project.global.exception;

import com.gamecrew.gamecrew_project.global.exception.type.ChatErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ChatExceptionImpl extends RuntimeException implements CustomChatException{

    private final ChatErrorCode chatErrorCode;

    @Override
    public HttpStatus getHttpStatus() {
        return chatErrorCode.getHttpStatus();
    }

    @Override
    public String getErrorMsg() {
        return chatErrorCode.getErrorMsg();
    }
}