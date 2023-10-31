package com.gamecrew.gamecrew_project.domain.user.controller;

import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckEmailRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckNicknameRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.request.EmailCodeRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.request.SignupRequestDto;
import com.gamecrew.gamecrew_project.domain.user.entity.Auth;
import com.gamecrew.gamecrew_project.domain.user.repository.AuthRepository;
import com.gamecrew.gamecrew_project.domain.user.service.SignUpService;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class SignUpController {
    private final SignUpService signupService;
    private final AuthRepository authRepository;

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

    //이메일 중복확인 및 인증코드 전송
    @PostMapping("/signup/email")
    public MessageResponseDto checkAndSendEmail(@RequestBody @Valid CheckEmailRequestDto requestDto) {
        String authCode = signupService.checkAndSendEmail(requestDto.getEmail());
        Auth auth = new Auth(requestDto.getEmail(), authCode);
        authRepository.save(auth);
        return new MessageResponseDto(Message.AVAILABLE_EMAIL + "인증 코드: " + authCode, HttpStatus.OK);
    }

    @PostMapping("/signup/email/check")
    @ResponseBody
    public MessageResponseDto verifyCode(@RequestBody EmailCodeRequestDto requestDto){
        return signupService.verifyCode(requestDto.getEmail(), requestDto.getCode());
    }

}