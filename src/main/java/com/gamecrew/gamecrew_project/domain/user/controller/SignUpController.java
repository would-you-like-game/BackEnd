package com.gamecrew.gamecrew_project.domain.user.controller;

import com.gamecrew.gamecrew_project.domain.user.dto.request.SignupRequestDto;
import com.gamecrew.gamecrew_project.domain.user.service.SignUpService;
import com.gamecrew.gamecrew_project.global.message.Message;
import com.gamecrew.gamecrew_project.global.message.MessageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SignUpController {
    private final SignUpService signupService;

    @PostMapping("/signup")
    public MessageResponseDto signup(@RequestBody @Valid SignupRequestDto requestDto){
        signupService.signup(requestDto);
        return new MessageResponseDto(Message.SIGNUP_SUCCESSFUL, HttpStatus.OK);
    }
}
