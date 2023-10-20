package com.gamecrew.gamecrew_project.domain.auth.controller;

import com.gamecrew.gamecrew_project.domain.auth.dto.request.LoginRequestDto;
import com.gamecrew.gamecrew_project.domain.auth.service.AuthService;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse res){
        authService.login(requestDto, res);
    }
}
