package com.gamecrew.gamecrew_project.domain.chat.controller;

import com.gamecrew.gamecrew_project.domain.chat.dto.ChatRoom;
import com.gamecrew.gamecrew_project.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
@CrossOrigin(origins = "http://13.125.224.16:8080")
public class ChatController {

    private final ChatService service;

    @PostMapping("/{userAId}/{userBId}")
    public ChatRoom createRoom(@PathVariable String userAId, @PathVariable String userBId){
        return service.createRoom(userAId, userBId);
    }

    @GetMapping
    public List<ChatRoom> findAllRooms(){
        return service.findAllRoom();
    }
}

