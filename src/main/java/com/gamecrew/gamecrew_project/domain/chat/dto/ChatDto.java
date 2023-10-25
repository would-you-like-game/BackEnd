package com.gamecrew.gamecrew_project.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDto {
    public enum MessageType{
        ENTER, TALK
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time; // 채팅 발송 시간
}
