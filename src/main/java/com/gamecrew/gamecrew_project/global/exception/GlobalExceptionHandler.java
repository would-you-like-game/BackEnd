package com.gamecrew.gamecrew_project.global.exception;

import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<MessageResponseDto> handleException(CustomException ex) {
        MessageResponseDto restApiException = new MessageResponseDto(ex.getMessage(), ex.getHttpStatus());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                ex.getHttpStatus()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        // 유효성 검증이 실패한 필드 중 첫 번째 필드의 에러 메시지를 가져옵니다.
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        MessageResponseDto response = new MessageResponseDto(errorMessage, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                // HTTP body
                response,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}