package com.gamecrew.gamecrew_project.domain.chat.dto;

import com.gamecrew.gamecrew_project.domain.chat.service.ChatService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {

    private String roomId;
    private String name; //채팅방 이름
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }
    public void handleAction(WebSocketSession session, ChatDto message, ChatService service){
        if (message.getType().equals(ChatDto.MessageType.ENTER)){
            sessions.add(session);

            message.setMessage(message.getMessage());
            sendMessage(message,service);
        }
    }
    public <T> void sendMessage(T message, ChatService service){
        sessions.parallelStream().forEach(sessions -> service.sendMessage(sessions,message));
    }
}
