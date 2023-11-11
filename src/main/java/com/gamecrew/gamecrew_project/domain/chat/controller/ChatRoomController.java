package com.gamecrew.gamecrew_project.domain.chat.controller;

import com.gamecrew.gamecrew_project.domain.chat.model.response.ChatRoomsResponseDto;
import com.gamecrew.gamecrew_project.domain.chat.service.ChatRoomService;
import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
import com.gamecrew.gamecrew_project.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/{receiverId}")
    public MessageResponseDto createChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @PathVariable Long receiverId){

        Long senderId = userDetails.getUser().getUserId();
        return chatRoomService.createChatRoom(senderId, receiverId);
    }

    @GetMapping("")
    public ChatRoomsResponseDto getChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestParam int page,
                                             @RequestParam int size){
        Long userId = userDetails.getUser().getUserId();
        return chatRoomService.getChatRooms(userId, page-1, size);
    }
}
