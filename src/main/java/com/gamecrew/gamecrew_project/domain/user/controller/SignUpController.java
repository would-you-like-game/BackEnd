package com.gamecrew.gamecrew_project.domain.user.controller;

import com.gamecrew.gamecrew_project.domain.auth.service.EmailService;
import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckEmailRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckNicknameRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.request.SignupRequestDto;
import com.gamecrew.gamecrew_project.domain.user.service.SignUpService;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SignUpController {
    private final SignUpService signupService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public MessageResponseDto signup(@RequestBody @Valid SignupRequestDto requestDto) {
        signupService.signup(requestDto);
        return new MessageResponseDto(Message.SIGNUP_SUCCESSFUL, HttpStatus.OK);
    }

    //닉네임 중복확인
    @PostMapping("/signup/nickname")
    public MessageResponseDto checkNickname(@RequestBody @Valid CheckNicknameRequestDto requestDto) {
        signupService.checkNickname(requestDto.getNickname());
        return new MessageResponseDto(Message.AVAILABLE_NICKNAME, HttpStatus.OK);
    }

    //이메일 중복확인
    @PostMapping("/signup/email")
    public MessageResponseDto checkEmail(@RequestBody @Valid CheckEmailRequestDto requestDto) {
        signupService.checkEmail(requestDto.getEmail());
        return new MessageResponseDto(Message.AVAILABLE_EMAIL, HttpStatus.OK);
    }
}