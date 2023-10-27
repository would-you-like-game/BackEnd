package com.gamecrew.gamecrew_project.domain.auth.controller;

import com.gamecrew.gamecrew_project.domain.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final EmailService emailService;

    @PostMapping("/signup/email/code")
    @ResponseBody
    public String mailConfirm(@RequestParam String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);
        log.info("인증코드 : " + code);
        return code;
    }

    @PostMapping("/signup/email/check")
    @ResponseBody
    public boolean verifyCode(@RequestParam String code){
        boolean isVerified = code.equals(emailService.getEPw());
        return isVerified;
    }


}
