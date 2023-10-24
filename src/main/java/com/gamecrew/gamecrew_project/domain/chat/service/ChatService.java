package com.gamecrew.gamecrew_project.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamecrew.gamecrew_project.domain.chat.dto.ChatRoom;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@Data
@Service
public class ChatService {

    private final ObjectMapper mapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init(){
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(){
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId){
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String userAId, String userBId){
        String roomId = generateRoomId(userAId, userBId);

        if(chatRooms.containsKey(roomId)) {
            return chatRooms.get(roomId);
        }

        ChatRoom room = ChatRoom.builder()
                .roomId(roomId)
                .name("Chat between " + userAId + " and " + userBId)
                .build();
        chatRooms.put(roomId,room);

        return room;
    }

    private String generateRoomId(String userAId, String userBid) {
        if(userAId.compareTo(userBid) > 0) {
            return userBid + "-" + userAId;
        } else {
            return userAId + "-" + userBid;
        }
    }

    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));;
        }catch (IOException e){
            log.error(e.getMessage(),e);
        }
    }
}
