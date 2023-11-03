package com.gamecrew.gamecrew_project.domain.user.controller;

import com.gamecrew.gamecrew_project.domain.user.dto.response.UserProfileResponseDto;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.service.UserService;
import com.gamecrew.gamecrew_project.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public UserProfileResponseDto getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return userService.getUserProfile(user);
    }
}
