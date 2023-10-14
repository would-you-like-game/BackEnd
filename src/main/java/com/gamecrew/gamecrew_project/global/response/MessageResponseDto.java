package com.gamecrew.gamecrew_project.global.response;

import org.springframework.http.HttpStatus;

public record MessageResponseDto(String message, HttpStatus status) {
}
