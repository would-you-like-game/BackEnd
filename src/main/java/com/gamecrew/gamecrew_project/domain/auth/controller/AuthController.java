package com.gamecrew.gamecrew_project.domain.auth.controller;

import com.gamecrew.gamecrew_project.domain.auth.dto.request.LoginRequestDto;
import com.gamecrew.gamecrew_project.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
