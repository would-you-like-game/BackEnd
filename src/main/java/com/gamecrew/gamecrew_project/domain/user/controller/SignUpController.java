package com.gamecrew.gamecrew_project.domain.user.controller;

import com.gamecrew.gamecrew_project.domain.user.service.EmailService;
import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckEmailRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckNicknameRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.request.SignupRequestDto;
import com.gamecrew.gamecrew_project.domain.user.service.SignUpService;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SignUpController {
    private final SignUpService signupService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public MessageResponseDto signup(@RequestBody @Valid SignupRequestDto requestDto){
        signupService.signup(requestDto);
        return new MessageResponseDto(Message.SIGNUP_SUCCESSFUL, HttpStatus.OK);
    }

    //닉네임 중복확인
    @PostMapping("/signup/nickname")
    public MessageResponseDto checkNickname(@RequestBody @Valid CheckNicknameRequestDto requestDto){
        signupService.checkNickname(requestDto.getNickname());
        return new MessageResponseDto(Message.AVAILABLE_NICKNAME, HttpStatus.OK);
    }

    //이메일 중복확인
    @PostMapping("/signup/email")
    public MessageResponseDto checkEmail(@RequestBody @Valid CheckEmailRequestDto requestDto) {
        signupService.checkEmail(requestDto.getEmail());
        return new MessageResponseDto(Message.AVAILABLE_EMAIL, HttpStatus.OK);
    }

    private int number; // 이메일 인증 숫자를 저장하는 변수

    // 인증 이메일 전송
    @PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(String mail) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            number = emailService.sendMail(mail);
            String num = String.valueOf(number);

            map.put("success", Boolean.TRUE);
            map.put("number", num);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }

    // 인증번호 일치여부 확인
    @GetMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestParam String userNumber) {

        boolean isMatch = userNumber.equals(String.valueOf(number));

        return ResponseEntity.ok(isMatch);
    }
}
