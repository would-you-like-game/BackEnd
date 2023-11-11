package com.gamecrew.gamecrew_project.domain.user.controller;

import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckNicknameRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.request.CheckPasswordRequestDto;
import com.gamecrew.gamecrew_project.domain.user.dto.response.UserProfileResponseDto;
import com.gamecrew.gamecrew_project.domain.user.entity.User;
import com.gamecrew.gamecrew_project.domain.user.service.UserS3Service;
import com.gamecrew.gamecrew_project.domain.user.service.UserService;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.response.constant.Message;
import com.gamecrew.gamecrew_project.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserS3Service userS3Service;

    @GetMapping("")
    public UserProfileResponseDto getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return userService.getUserProfile(user);
    }

    @PutMapping("/nickname")
    public MessageResponseDto updateUserNickname(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @RequestBody @Valid CheckNicknameRequestDto requestDto){
        Long userId = userDetails.getUser().getUserId();
        userService.updateUserNickname(userId, requestDto);

        return new MessageResponseDto(Message.NICKNAME_UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @PostMapping("/password")
    public MessageResponseDto checkUserPassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @RequestBody @Valid CheckPasswordRequestDto requestDto){
        User user = userDetails.getUser();
        userService.checkUserPassword(user, requestDto);

        return new MessageResponseDto(Message.PASSWORD_MATCH_SUCCESSFUL, HttpStatus.OK);
    }


    @PutMapping("/password")
    public MessageResponseDto updateUserPassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @RequestBody @Valid CheckPasswordRequestDto requestDto){
        Long userId = userDetails.getUser().getUserId();
        userService.updateUserPassword(userId, requestDto);

        return new MessageResponseDto(Message.PASSWORD_UPDATE_SUCCESSFUL, HttpStatus.OK);
    }


    @PutMapping("/image")
    public MessageResponseDto updateUserImage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @RequestPart("userImg") MultipartFile userImg) throws IOException {
        Long userId = userDetails.getUser().getUserId();
        userS3Service.updateUserImage(userId, userImg);

        return new MessageResponseDto(Message.USER_IMG_UPDATE_SUCCESSFUL, HttpStatus.OK);
    }
}
