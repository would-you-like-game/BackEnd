package com.gamecrew.gamecrew_project.domain.auth.service;

import com.gamecrew.gamecrew_project.domain.auth.dto.request.LoginRequestDto;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.repository.UserRepository;
import com.gamecrew.gamecrew_project.global.exception.MismatchException;
import com.gamecrew.gamecrew_project.global.exception.constant.ErrorMessage;
import com.gamecrew.gamecrew_project.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public ResponseEntity<Map<String, String>> login(LoginRequestDto requestDto, HttpServletResponse res) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new MismatchException(ErrorMessage.NON_EXISTENT_EMAIL, HttpStatus.UNAUTHORIZED)
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new MismatchException(ErrorMessage.PASSWORD_MISMATCH, HttpStatus.UNAUTHORIZED);
        }
        // 인증에 성공하면 JWT 토큰을 생성합니다.
        String token = jwtUtil.createToken(email);

        // 토큰과 메시지를 응답 바디에 담아 보냅니다.
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("token", token);

        return ResponseEntity.ok(responseMap);
    }
}