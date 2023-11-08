//package com.gamecrew.gamecrew_project.domain.chat.controller;
//
//import com.gamecrew.gamecrew_project.global.response.CustomResponse;
//import com.gamecrew.gamecrew_project.domain.chat.service.ChatRoomService;
//import com.gamecrew.gamecrew_project.global.response.MessageResponseDto;
//import com.gamecrew.gamecrew_project.global.response.constant.Message;
//import com.gamecrew.gamecrew_project.global.security.UserDetailsImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/chatroom")
//public class ChatRoomController {
//    private final ChatRoomService chatRoomService;
//
//    @GetMapping
//    public MessageResponseDto createChatRoom(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
//            @RequestParam Long receiver
//    ) {
//        chatRoomService.createChatRoom(userDetails.getUser(), receiver);
//        return new MessageResponseDto(Message.CREATE_CHAT_ROOM, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public CustomResponse<?> getChatRoomList(
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) {
//        return CustomResponse.success("성공적으로 채팅방 목록을 조회하셨습니다.", chatRoomService.searchProfileList(userDetails.getUser()));
//    }
//}
