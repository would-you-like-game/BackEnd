package com.gamecrew.gamecrew_project.global.message;

import org.springframework.http.HttpStatus;

public record MessageResponseDto(String message, HttpStatus status) {
}
