package com.gamecrew.gamecrew_project.domain.chat.controller;

import com.gamecrew.gamecrew_project.domain.chat.model.message.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;

    @MessageMapping("/send/message")
    public void sendMessage(@Payload ChatMessage message) {
        template.convertAndSend("/sub/" + message.roomKey(), message);
    }
}
